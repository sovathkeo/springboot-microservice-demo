package com.jdbcdemo.common.configurations;

import com.jdbcdemo.common.filter.AuthenticationFilter;
import com.jdbcdemo.common.filter.ExceptionFilter;
import com.jdbcdemo.common.filter.LogFilter;
import com.jdbcdemo.common.security.authprovider.apikeyauth.ApiKeyAuthenticationProvider;
import com.jdbcdemo.common.security.authprovider.basicauth.BasicAuthenticationProvider;
import com.jdbcdemo.common.security.authprovider.bearerauth.BearerAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationManager(authenticationManager())
            .addFilterBefore(new ExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new LogFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider bearerAuthenticationProvider() {
        return new BearerAuthenticationProvider();
    }

    @Bean
    public AuthenticationProvider basicAuthenticationProvider() {
        return new BasicAuthenticationProvider();
    }

    @Bean AuthenticationProvider apiKeyAuthenticationProvider() { return new ApiKeyAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(
            bearerAuthenticationProvider(),
            basicAuthenticationProvider(),
            apiKeyAuthenticationProvider()
        ));
    }

    @Bean
    public AuthenticationFilter oAuthAuthenticationFilter() {
        var adminAuthFilter = new AuthenticationFilter(authenticationManager());
        adminAuthFilter.setAllowSessionCreation(true);
        return adminAuthFilter;
    }
}
