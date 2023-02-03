select *
from student;

select *
from student
where age > 16
  and age < 20;

select name
from student;

select *
from student
where name like '%е%';

select *
from student
where age < student.id;

select *
from student
ORDER BY age;

SELECT *
FROM student
ORDER BY id DESC
LIMIT 5;







