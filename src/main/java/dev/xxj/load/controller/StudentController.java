package dev.xxj.load.controller;

import dev.xxj.load.service.StudentControllerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentControllerService service;
    private final MeterRegistry meterRegistry;

    public StudentController(StudentControllerService service,
                             MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
        Gauge.builder("student.succeedRate", this::succeedRate)
                .description("Successful response rate of student controller")
                .register(meterRegistry);
    }

    @GetMapping
    @RateLimiter(name = "studentLimiter")
    ResponseEntity<Object> findStudents(@RequestParam("amount") int amount) {
        return service.getStudents(amount);
    }

    private double succeedRate() {
        var total = meterRegistry.counter("student.req").count();
        if (total == 0) return 0.0;
        return meterRegistry.counter("student.req.success").count() / total;
    }
}
