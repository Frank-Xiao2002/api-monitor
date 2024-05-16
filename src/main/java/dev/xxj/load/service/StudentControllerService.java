package dev.xxj.load.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentControllerService {
    private final StudentService service;
    private final Counter totalSuccess;

    @Autowired
    public StudentControllerService(StudentService service,
                                    MeterRegistry meterRegistry) {
        this.service = service;
        this.totalSuccess = Counter.builder("student.req.success")
                .description("Total successful requests")
                .register(meterRegistry);
    }

    public ResponseEntity<Object> getStudents(int amount) {
        try {
            var list = service.getStudents(amount);
            totalSuccess.increment();
            return ResponseEntity.ok()
                    .body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
