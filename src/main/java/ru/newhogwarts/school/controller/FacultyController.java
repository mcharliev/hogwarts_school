package ru.newhogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.newhogwarts.school.model.Faculty;
import ru.newhogwarts.school.model.Student;
import ru.newhogwarts.school.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable int id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("/find")
    public ResponseEntity<Faculty> findByNameOrColor(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank() ||
                color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/findFacultyId/{id}")
    public ResponseEntity<Faculty> findStudentsByFaculty(@PathVariable int id) {
        if (id > 0) {
            return ResponseEntity.ok(facultyService.findFacultyByStudentsId(id));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
