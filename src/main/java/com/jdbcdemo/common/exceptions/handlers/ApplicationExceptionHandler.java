package com.jdbcdemo.common.exceptions.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.common.alerts.TelegramBot;
import com.jdbcdemo.common.exceptions.models.ApplicationException;
import com.jdbcdemo.common.exceptions.models.ApplicationRuntimeException;
import com.jdbcdemo.dtos.responses.Response;
import com.jdbcdemo.services.tracing.CorrelationService;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private CorrelationService correlationService;

    @ExceptionHandler(value = {RuntimeException.class, ApplicationException.class , ApplicationRuntimeException.class})
    public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) throws JsonProcessingException {
        return handleApplicationException(ex, request);
    }

    @ExceptionHandler(value = {Exception.class,  AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleCustomException1(RuntimeException ex, WebRequest request) throws JsonProcessingException{

        var applicationException = tryGetApplicationException(ex);

        if (applicationException.isPresent()) {
            return handleApplicationException(applicationException.get(), request);
        }

        var correlationId = correlationService.getCorrelationId();

        var body = Response.failure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(),"request failed", correlationId);

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        @Nullable HttpHeaders headers,
        @Nullable HttpStatusCode status,
        @Nullable WebRequest request ) {

        var correlationId = correlationService.getCorrelationId();
        var statusCode = HttpStatus.UNPROCESSABLE_ENTITY;

        var body = Response.failure(statusCode.toString(), "Validation failure",ex.getMessage(), correlationId);

        return handleExceptionInternal(
            ex,
            body,
            HttpHeaders.EMPTY,
            statusCode,
            Objects.requireNonNull(request));
    }

    private ResponseEntity<Object> handleApplicationException(
        Exception ex,
        WebRequest request) throws JsonProcessingException{

        var correlationId = correlationService.getCorrelationId();
        var e = tryGetApplicationException(ex);
        HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        var originalError = tryGetOriginalErrorMessage(ex);

        var body = Response.failure(statusCode.toString(), originalError, "something went wrong", correlationId);

        var bodyString = mapper.writeValueAsString(body);
        logger.error(bodyString, e);

        telegramBot.sendMessageAsync(body);

        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, statusCode, request);
    }

    private Optional<ApplicationException> tryGetApplicationException(Exception exception) {
        var ex = exception.getCause();
        while (true) {
            if (ex == null) {
                return Optional.empty();
            }
            if (ex instanceof ApplicationException) {
                return Optional.of((ApplicationException) ex);
            }
            ex = ex.getCause();
        }
    }

    private String tryGetOriginalErrorMessage(Exception e) {

        Throwable cause = e.getCause();

        while (cause != null) {
            if (cause.getCause() == null) {
                break;
            }
            cause = cause.getCause();
        }

        return cause != null ? cause.getMessage() : e.getMessage();
    }
}
