package kh.com.cellcard.common.validators.jsonschema;

import com.networknt.schema.ValidationMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class JsonValidationExceptionHandler {
    @ExceptionHandler({JsonValidationFailedException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(Exception exception) {
            List<String> messages = new ArrayList<>();
            String msgDes = "Json validation failed";

            if(exception instanceof  JsonValidationFailedException){
                if(!((JsonValidationFailedException)exception).getValidationMessages().isEmpty()){
                    messages = ((JsonValidationFailedException)exception).getValidationMessages().stream()
                        .map(ValidationMessage::getMessage)
                        .collect(Collectors.toList());
                }
            }

            if(exception instanceof ConstraintViolationException){
                if(!((ConstraintViolationException)exception).getConstraintViolations().isEmpty()){
                    messages = ((ConstraintViolationException) exception).getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList());
                    msgDes = "Constraint validation failed";
                }
            }

            if(!(exception instanceof ConstraintViolationException)
                    && !(exception instanceof JsonValidationFailedException)
            ){
                messages.add(exception.getMessage().trim());
                msgDes = "Internal Server Error";
            }

            System.out.println("Fail Schema :" + messages);
            return ResponseEntity.badRequest().body(Map.of(
                    "message", msgDes,
                    "details", messages
            ));
    }
}
