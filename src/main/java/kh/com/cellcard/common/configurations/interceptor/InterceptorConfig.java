package kh.com.cellcard.common.configurations.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import kh.com.cellcard.common.interceptor.InitializeRequestInterceptor;
import kh.com.cellcard.common.validators.jsonschema.JsonSchemaValidatingArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Objects;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Override
    public void addInterceptors(@Nullable InterceptorRegistry registry ) {

        Objects.requireNonNull(registry)
            .addInterceptor(new InitializeRequestInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JsonSchemaValidatingArgumentResolver(objectMapper, resourcePatternResolver));
    }
}
