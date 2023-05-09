package lt.bongibau.smapi.api.validator.schema;

import java.util.List;

public class SMSchema<T> {

    private final List<SMSchemaField<T, ?>> fields;

    public SMSchema(List<SMSchemaField<T, ?>> fields) {
        this.fields = fields;
    }

    public List<SMSchemaField<T, ?>> fields() {
        return this.fields;
    }

    public void validate() {
        this.fields().forEach(SMSchemaField::validate);
    }

}
