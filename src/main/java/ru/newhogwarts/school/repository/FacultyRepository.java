package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newhogwarts.school.model.Faculty;

public interface FacultyRepository extends JpaRepository <Faculty,Integer> {
}
