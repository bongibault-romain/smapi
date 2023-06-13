package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaField;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaFieldBuilder;

import java.util.ArrayList;
import java.util.List;

public class SMSchemaBuilder<T> {

    private final List<SMSchemaField<T, ?>> fields = new ArrayList<>();

    public <F> SMSchemaFieldBuilder<T, F> addField(SMAdapter<T, F> adapter) {
        return new SMSchemaFieldBuilder<>(this, adapter);
    }

    public <F> SMSchemaFieldBuilder<T, F> addField(String name, SMAdapter<T, F> adapter) {
        return this.addField(adapter).setIdentifier(name);
    }

    public SMSchema<T> build() {
        return new SMSchema<>(this.fields);
    }

    public <F> SMSchemaBuilder<T> addField(SMSchemaField<T, F> field) {
        this.fields.add(field);

        return this;
    }

}
