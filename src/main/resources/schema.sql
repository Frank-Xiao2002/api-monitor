create table if not exists student
(
    id     int auto_increment
        primary key,
    name   varchar(255)    null,
    gender enum ('m', 'f') null,
    age    tinyint         null
);