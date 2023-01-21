package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newhogwarts.school.model.Student;

import java.util.Collection;


public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findStudentByAgeBetweenIgnoreCase(int minAge, int maxAge);

    Collection<Student> findByFaculty_Id(int id);
}
