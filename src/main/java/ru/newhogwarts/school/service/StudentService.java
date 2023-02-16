package ru.newhogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.newhogwarts.school.model.Student;
import ru.newhogwarts.school.repository.StudentRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.debug("addStudent method was called");
        return studentRepository.save(student);
    }

    public Student getStudent(int id) {
        logger.debug("getStudent method was called");
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        logger.debug("editStudent method was called");
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {
        logger.debug("deleteStudent method was called");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudents(int age) {
        logger.debug("findStudents method was called");
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }

    public Collection<Student> findByAge(int minAge, int maxAge) {
        logger.debug("findByAge method was called");
        return studentRepository.findStudentByAgeBetweenIgnoreCase(minAge, maxAge);
    }

    public Collection<Student> findStudentFromFaculty(int id) {
        logger.debug("findStudentFromFaculty method was called");
        return studentRepository.findByFaculty_Id(id);
    }

    public Integer getCountOfStudents() {
        logger.debug("getCountOfStudents method was called");
        return studentRepository.getCountOfStudents();
    }

    public Double getAvgAgeOfStudents() {
        logger.debug("getAvgAgeOfStudents method was called");
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("getLastFiveStudents method was called");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getStudentsWhoseNamesStartWithLetterA() {
        logger.debug("getStudentsWhoseNamesStartWithLetterA method was called");
        return studentRepository.findAll()
                .stream()
                .filter(student -> student
                        .getName()
                        .startsWith("А"))
                .sorted(Comparator.comparing(Student::getName))
                .map(student -> student.getName().toUpperCase())
                .collect(Collectors.toList());
    }

    public void printAllStudentsNames() {
        List<String> studentsNames = studentRepository.findAll()
                .stream()
                .map(Student::getName).toList();
        for (int i = 0; i < 2; i++) {
            System.out.println(studentsNames.get(i));
        }
        new Thread(() ->
        {
            for (int i = 2; i < 4; i++) {
                System.out.println(studentsNames.get(i));
            }
        }
        ).start();
        new Thread(() ->
        {
            for (int i = 4; i < 6; i++) {
                System.out.println(studentsNames.get(i));
            }
        }
        ).start();
        new Thread(() ->
        {
            for (int i = 6; i < 8; i++) {
                System.out.println(studentsNames.get(i));
            }
        }
        ).start();
    }

    public void printAllStudentsNamesInSynchronizedStream() throws InterruptedException {
        print(0);
        print(1);
        Thread thread_1 = new Thread(() ->
        {
            print(2);
            print(3);
        }
        );
        thread_1.start();
        thread_1.join();
        Thread thread_2 =   new Thread(() ->
        {
            print(4);
            print(5);
        }
        );
        thread_2.start();
        thread_2.join();
        Thread thread_3 = new Thread(() ->
        {
            print(6);
            print(7);
        }
        );
        thread_3.start();
        thread_3.join();
    }

    private void print(int index) {
        List<String> studentsNames = studentRepository.findAll()
                .stream()
                .map(Student::getName).toList();
        System.out.println(studentsNames.get(index));
    }
}
