package ru.newhogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.newhogwarts.school.model.Faculty;
import ru.newhogwarts.school.repository.FacultyRepository;


import java.util.*;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(int id) {
        logger.debug("getFaculty method was called");
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("getAllFaculties method was called");
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("editFaculty method was called");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(int id) {
        logger.debug("deleteFaculty method was called");
        facultyRepository.deleteById(id);
    }

    public Set<Faculty> findByColor(String color) {
        logger.debug("findByColor method was called");
        return facultyRepository.findFacultyByColor(color);
    }

    public Set<Faculty> findByColorOrNameIgnoreCase(String color, String name) {
        logger.debug("findByColorOrNameIgnoreCase method was called");
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    public Faculty findFacultyByStudentsId(int id) {
        logger.debug("findFacultyByStudentsId method was called");
        return facultyRepository.findFacultyByStudentsId(id);
    }
}

