package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.adapter.SMAdapter;

import java.util.ArrayList;
import java.util.List;

public class SMSchemaBuilder<T> {

    private final List<SMSchemaField<T, ?>> fields = new ArrayList<>();

    public <F> SMSchemaFieldBuilder<T, F> addField(SMAdapter<T, F> adapter) {
        return new SMSchemaFieldBuilder<>(this, adapter);
    }

    public SMSchema<T> build() {
        return new SMSchema<>(this.fields);
    }

    <F> SMSchemaBuilder<T> addField(SMSchemaField<T, F> field) {
        this.fields.add(field);

        return this;
    }

}
