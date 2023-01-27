package ru.newhogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.newhogwarts.school.model.Faculty;
import ru.newhogwarts.school.repository.FacultyRepository;


import java.util.*;

@Service
public class FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(int id) {
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(int id) {
        facultyRepository.deleteById(id);
    }

    public Set<Faculty> findByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

    public Set<Faculty> findByColorOrNameIgnoreCase(String color, String name) {
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    public Faculty findFacultyByStudentsId(int id) {
        return facultyRepository.findFacultyByStudentsId(id);
    }
}

