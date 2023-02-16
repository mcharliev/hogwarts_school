package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.newhogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findStudentByAgeBetweenIgnoreCase(int minAge, int maxAge);

    Collection<Student> findByFaculty_Id(int id);

    @Query(value = "SELECT count (*) FROM Student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();


}
