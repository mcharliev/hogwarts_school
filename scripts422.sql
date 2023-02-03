ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 16);

ALTER TABLE student
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty
    ADD CONSTRAINT color_name_unique UNIQUE (color, name);

ALTER TABLE student
    ALTER COLUMN age SET default 20;

CREATE TABLE person
(
    Id               INTEGER primary key,
    Name             VARCHAR NOT NULL,
    Age              INTEGER CHECK ( Age > 0 ),
    HasDriverLicense BOOLEAN,
    CarsId           INTEGER REFERENCES car (Id)
);

CREATE TABLE car
(
    Id    Integer primary key,
    Brand VARCHAR NOT NULL,
    Model VARCHAR NOT NULL,
    Price MONEY
);

SELECT student.name, student.age,faculty.name
FROM student
INNER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name,student.age from student
INNER JOIN avatar a on student.id = a.student_id;






