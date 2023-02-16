package ru.newhogwarts.school.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Integer id;

    private String filePath;
    private int fileSize;
    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar)) return false;

        Avatar avatar = (Avatar) o;

        if (getFileSize() != avatar.getFileSize()) return false;
        if (getId() != null ? !getId().equals(avatar.getId()) : avatar.getId() != null) return false;
        if (getFilePath() != null ? !getFilePath().equals(avatar.getFilePath()) : avatar.getFilePath() != null)
            return false;
        if (getMediaType() != null ? !getMediaType().equals(avatar.getMediaType()) : avatar.getMediaType() != null)
            return false;
        if (!Arrays.equals(getData(), avatar.getData())) return false;
        return getStudent() != null ? getStudent().equals(avatar.getStudent()) : avatar.getStudent() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFilePath() != null ? getFilePath().hashCode() : 0);
        result = 31 * result + getFileSize();
        result = 31 * result + (getMediaType() != null ? getMediaType().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getData());
        result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
        return result;
    }
}
