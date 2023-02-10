package ru.newhogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.newhogwarts.school.model.Avatar;
import ru.newhogwarts.school.model.Student;
import ru.newhogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private AvatarRepository avatarRepository;
    private StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Integer studentId, MultipartFile file) throws IOException {
        logger.debug("uploadAvatar method was called");
        Student student = studentService.getStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int) file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Integer id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.debug("getAllAvatars method was called");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
