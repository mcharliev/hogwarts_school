package ru.newhogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.newhogwarts.school.controller.StudentController;
import ru.newhogwarts.school.model.Student;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.URI;
import java.util.Collection;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HogwartsAppTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoad() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student student = givenStudentWith("studentName", 18);
        ResponseEntity<Student> response = whenSendingCreateStudentRequest(
                getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(response);
    }

    @Test
    public void testGetStudentsById() throws Exception {
        Student student = givenStudentWith("studentName", 18);
        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),
                student);
        thenStudentHasBeenCreated(createResponse);
        Student createStudent = createResponse.getBody();
        thenStudentWithIdHasBeenFound(createStudent.getId(), createStudent);
    }

    @Test
    public void testFindByAge() {
        Student student1 = givenStudentWith("studentName1", 18);
        Student student2 = givenStudentWith("studentName2", 25);
        Student student3 = givenStudentWith("studentName3", 28);
        Student student4 = givenStudentWith("studentName4", 32);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student2);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student3);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student4);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "25");
        thenStudentAreFoundByCriteria(queryParams, student2);
    }

    @Test
    public void testFindByAgeBetween() {
        Student student1 = givenStudentWith("studentName1", 18);
        Student student2 = givenStudentWith("studentName2", 25);
        Student student3 = givenStudentWith("studentName3", 28);
        Student student4 = givenStudentWith("studentName4", 32);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student2);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student3);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student4);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("minAge", "20");
        queryParams.add("maxAge", "30");
        thenStudentAreFoundByCriteria(queryParams, student2, student3);
    }

    @Test
    public void testUpdate() {
        Student student = givenStudentWith("studentName", 25);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),
                student);
        thenStudentHasBeenCreated(responseEntity);
        Student createStudent = responseEntity.getBody();

        whenUpdatingStudent(createStudent, 32, "newName");
        thenStudentHasBeenUpdated(createStudent, 32, "newName");
    }

    @Test
    public void testDelete() {
        Student student = givenStudentWith("studentName", 25);
        ResponseEntity<Student> responseEntity = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(),
                student);
        thenStudentHasBeenCreated(responseEntity);
        Student createStudent = responseEntity.getBody();
        whenDeletingStudent(createStudent);
        thenStudentNotFound(createStudent);
    }

    private Student givenStudentWith(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }

    private ResponseEntity<Student> whenSendingCreateStudentRequest(URI uri, Student student) {
        return restTemplate.postForEntity(uri, student, Student.class);
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/students");
    }

    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    private void thenStudentWithIdHasBeenFound(Integer studentId, Student student) {
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void thenStudentAreFoundByCriteria(MultiValueMap<String, String> queryParams, Student... student) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();
        ResponseEntity<Collection<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Collection<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(student);
    }

    private void resetIds(Collection<Student> students) {
        students.forEach(e -> e.setId(null));
    }

    private void whenUpdatingStudent(Student createStudent, int newAge, String newName) {
        createStudent.setAge(newAge);
        createStudent.setName(newName);
        restTemplate.put(getUriBuilder().build().toUri(), createStudent);
    }

    private void thenStudentHasBeenUpdated(Student createStudent, int newAge, String newName) {
        URI getUri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createStudent.getId()).toUri();
        ResponseEntity<Student> updatedStudent = restTemplate.getForEntity(getUri, Student.class);
        Assertions.assertThat(updatedStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updatedStudent.getBody()).isNotNull();
        Assertions.assertThat(updatedStudent.getBody().getAge()).isEqualTo(newAge);
        Assertions.assertThat(updatedStudent.getBody().getName()).isEqualTo(newName);
    }

    private void whenDeletingStudent(Student createdStudent) {
        restTemplate.delete(getUriBuilder().path("/{id}")
                .buildAndExpand(createdStudent.getId()).toUri());
    }

    private void thenStudentNotFound(Student createStudent) {
        URI getUri = getUriBuilder().path("/{id}").buildAndExpand(createStudent.getId()).toUri();
        ResponseEntity<Student> emptyRs = restTemplate.getForEntity(getUri, Student.class);
        Assertions.assertThat(emptyRs.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
