package kh.com.cellcard;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import kh.com.cellcard.common.constant.HttpHeaderConstant;
import kh.com.cellcard.common.constant.SystemEnvironmentConstant;
import kh.com.cellcard.common.constant.SystemPropertyNameConstant;
import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.common.registration.RegisterService;
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
        contact = @Contact(name = "Backend Developer", email = "backend@cellcard.com.kh")
    ),
    servers = {@Server(url = "http://localhost:8080"), @Server(url = "http://api.springboot-demo:8080")},
    security = { @SecurityRequirement(name = "X-API-KEY") }
)
public class Application {
    private static final String DEFAULT_CONFIG_FILE = "application";
    public static void main(String[] args) {
        MDC.put(HttpHeaderConstant.CORRELATION_ID, new UUID(0L, 0L).toString());
        System.setProperty(SystemPropertyNameConstant.SPRING_CONFIG_NAME, buildConfigFile());
        SpringApplication.run(Application.class, args);
    }

    private static String buildConfigFile() {
        var profile = System.getenv(SystemEnvironmentConstant.SPRING_PROFILES_ACTIVE);
        return StringHelper.isNullOrEmpty(profile)
            ? DEFAULT_CONFIG_FILE
            : "%s-%s".formatted(DEFAULT_CONFIG_FILE, profile);
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
