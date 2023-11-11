package com.jdbcdemo;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.jdbcdemo.common.constant.HttpHeaderConstant;
import com.jdbcdemo.common.registration.RegisterService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.UUID;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "X-API-KEY", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(
    info = @Info(
        title = "spring-boot-demo-api",
        description = "demo spring-boot microservice",
        contact = @Contact(name = "KEO SOVATH", email = "sovathkeo123@gmail.com")
    ),
    servers = {@Server(url = "http://localhost:8080"), @Server(url = "http://api.springboot-demo:8080")},
    security = { @SecurityRequirement(name = "X-API-KEY") }
)
public class Application {

    public static void main(String[] args) {
        MDC.put(HttpHeaderConstant.CORRELATION_ID, new UUID(0L, 0L).toString());
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        RegisterService.registers();
    }

    @Bean
    Pipeline pipeline(
            ObjectProvider<Command.Handler> commandHandler,
            ObjectProvider<Notification.Handler> notificationHandlers,
            ObjectProvider<Command.Middleware> middlewares) {

        return new Pipelinr()
                .with(commandHandler::stream)
                .with(notificationHandlers::stream)
                .with(middlewares::stream);
    }
}
