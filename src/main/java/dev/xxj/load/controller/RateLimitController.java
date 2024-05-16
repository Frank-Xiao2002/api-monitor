package dev.xxj.load.controller;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/rate")
public class RateLimitController {
    private final RateLimiterRegistry rateLimiterRegistry;

    public RateLimitController(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Integer>> rate() {
        var limitForPeriod = rateLimiterRegistry.rateLimiter("studentLimiter").getRateLimiterConfig()
                .getLimitForPeriod();
        return ResponseEntity.ok().body(new HashMap<>() {{
            put("rate", limitForPeriod);
        }});
    }

    @PutMapping
    public ResponseEntity<String> changeRate(@RequestParam("rate") int rate) {
        rateLimiterRegistry.rateLimiter("studentLimiter").changeLimitForPeriod(rate);
        return ResponseEntity.ok().body("Rate changed to " + rate);
    }
}
