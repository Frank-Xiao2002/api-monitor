package dev.xxj.load.service;

import dev.xxj.load.model.Student;
import dev.xxj.load.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents(int amount) {
        if (amount > 0 && amount <= 1000) {
            List<Student> allStudents = repository.findAll();
            Collections.shuffle(allStudents);
            return allStudents.subList(0, Math.min(amount, allStudents.size()));
        }
        throw new IllegalArgumentException("Amount should be between 1 and 1000");
    }

}
