package io.github.hdsp.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.hdsp.web.interceptor.HdspTraceIdInterceptor;

@AutoConfiguration
public class HdspWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor traceIdInterceptor() {
        return new HdspTraceIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
    }
}
