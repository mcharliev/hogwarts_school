--liquibase formatted sql

--changeset mcharliev:1
CREATE INDEX student_name_index ON student(name);

--changeset mcharliev:2
CREATE INDEX faculty_colorOrName_index ON faculty(color,name);
