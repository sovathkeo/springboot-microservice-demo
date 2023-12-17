package com.jdbcdemo.common.configurations.interceptor;

import com.jdbcdemo.common.interceptor.InitializeRequestInterceptor;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(@Nullable InterceptorRegistry registry ) {

        Objects.requireNonNull(registry)
            .addInterceptor(new InitializeRequestInterceptor());
    }
}
