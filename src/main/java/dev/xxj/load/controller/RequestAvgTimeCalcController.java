package dev.xxj.load.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/time")
public class RequestAvgTimeCalcController {
    private final HashMap<Integer, Double> avgTime = new HashMap<>();
    private final HashMap<Integer, ArrayList<Long>> timeRecords = new HashMap<>();

    public RequestAvgTimeCalcController() {
        for (int i = 0; i < 10; i++) {
            timeRecords.put(i, new ArrayList<>());
        }
    }

    public void update(int amount, Long time) {
        if (amount > 0 && amount <= 1000) {
            int category = (amount - 1) / 100;
            timeRecords.get(category).add(time);
            log.info("Added time {} to category {}", time, category);
        }
    }

    @GetMapping
    public HashMap<Integer, Double> calcAvg() {
        for (int i = 0; i < 10; i++) {
            var list = timeRecords.get(i);
            int index = i;
            list.stream().mapToLong(Long::longValue).average().ifPresentOrElse(
                    avg -> avgTime.put(index, avg),
                    () -> avgTime.put(index, 0.0)
            );
        }
        return avgTime;
    }

}
