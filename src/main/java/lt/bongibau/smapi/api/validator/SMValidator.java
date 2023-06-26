package lt.bongibau.smapi.api.validator;

import lt.bongibau.smapi.api.validator.schema.SMSchema;

public interface SMValidator<T> {

    SMSchema<T> getSchema();

    String getMessage(String path);

}
