package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newhogwarts.school.model.Faculty;

import java.util.Set;


public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Set<Faculty> findByColorOrNameIgnoreCase(String color, String name);

    Set<Faculty> findFacultyByColor(String color);

    Faculty findFacultyByStudentsId(int id);

}
