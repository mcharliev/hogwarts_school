package ru.newhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newhogwarts.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    Optional<Avatar> findByStudentId(Integer studentId);
}
