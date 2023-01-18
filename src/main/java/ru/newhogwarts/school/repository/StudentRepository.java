package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newhogwarts.school.model.Student;


public interface StudentRepository extends JpaRepository <Student,Integer> {
}
