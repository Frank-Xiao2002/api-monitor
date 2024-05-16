create procedure InsertStudents()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE random_name VARCHAR(255);
    DECLARE random_gender CHAR(1);
    DECLARE random_age INT;

    WHILE i < 100000
        DO
            SET random_name = CONCAT('Student', i);
            SET random_gender = IF(RAND() < 0.5, 'm', 'f');
            SET random_age = FLOOR(18 + RAND() * 10);

            INSERT INTO student (name, gender, age) VALUES (random_name, random_gender, random_age);

            SET i = i + 1;
        END WHILE;
END;

call InsertStudents();