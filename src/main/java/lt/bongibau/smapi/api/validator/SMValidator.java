package lt.bongibau.smapi.api.validator;

import lt.bongibau.smapi.api.validator.schema.SMSchema;
import lt.bongibau.smapi.api.validator.schema.SMSchemaContext;

public interface SMValidator<Entry, Context extends SMSchemaContext<Entry>> {

    SMSchema<Entry, Context> getSchema();

    String getMessage(String path);

}
