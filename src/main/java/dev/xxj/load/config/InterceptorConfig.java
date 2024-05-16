package dev.xxj.load.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final StudentRequestInterceptor studentRequestInterceptor;

    public InterceptorConfig(StudentRequestInterceptor studentRequestInterceptor) {
        this.studentRequestInterceptor = studentRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(studentRequestInterceptor)
                .addPathPatterns("/api/student/**");
    }

}
