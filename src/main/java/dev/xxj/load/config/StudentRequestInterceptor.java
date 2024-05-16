package dev.xxj.load.config;

import dev.xxj.load.controller.RequestAvgTimeCalcController;
import io.github.resilience4j.core.StopWatch;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class StudentRequestInterceptor implements HandlerInterceptor {
    private final MeterRegistry meterRegistry;
    private StopWatch stopWatch;
    private final RequestAvgTimeCalcController requestAvgTimeCalcController;

    public StudentRequestInterceptor(MeterRegistry meterRegistry,
                                     RequestAvgTimeCalcController requestAvgTimeCalcController) {
        this.meterRegistry = meterRegistry;
        this.requestAvgTimeCalcController = requestAvgTimeCalcController;
        Counter.builder("student.req")
                .description("Total requests on student controller")
                .register(meterRegistry);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        stopWatch = StopWatch.start();
        meterRegistry.counter("student.req").increment();
        log.info("Request received for {}", request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        var duration = stopWatch.stop();
        log.info("Request took: {} milliseconds", duration.toMillis());
        var amount = request.getParameterMap().get("amount")[0];
        requestAvgTimeCalcController.update(Integer.parseInt(amount), duration.toMillis());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
