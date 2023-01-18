package ru.newhogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.newhogwarts.school.model.Student;
import ru.newhogwarts.school.repository.StudentRepository;


import java.util.*;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(int id) {
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudents(int age) {
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }
}
