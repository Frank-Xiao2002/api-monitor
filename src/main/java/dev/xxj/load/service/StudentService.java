package dev.xxj.load.service;


import dev.xxj.load.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(int amount);
}
