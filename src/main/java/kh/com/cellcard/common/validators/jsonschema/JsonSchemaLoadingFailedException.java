package kh.com.cellcard.common.validators.jsonschema;

public class JsonSchemaLoadingFailedException extends RuntimeException {

    public JsonSchemaLoadingFailedException(String message) {
        super(message);
    }

    public JsonSchemaLoadingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}