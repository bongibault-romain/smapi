package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;

import java.util.List;

public class SchemaValidationException extends Exception {
    private final List<SchemaFieldException> errors;

    public <T> SchemaValidationException(List<SchemaFieldException> errors) {
        this.errors = errors;
    }

    public List<SchemaFieldException> getErrors() {
        return errors;
    }
}
