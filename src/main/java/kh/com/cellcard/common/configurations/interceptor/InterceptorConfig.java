package kh.com.cellcard.common.configurations.interceptor;

import jakarta.annotation.Nullable;
import kh.com.cellcard.common.interceptor.InitializeRequestInterceptor;
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
