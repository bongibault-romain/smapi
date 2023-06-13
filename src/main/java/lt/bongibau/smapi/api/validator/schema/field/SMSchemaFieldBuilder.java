package lt.bongibau.smapi.api.validator.schema.field;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.SMSchemaBuilder;

import java.util.ArrayList;
import java.util.List;

public class SMSchemaFieldBuilder<T, F> {

    private final SMSchemaBuilder<T> builder;

    private final SMAdapter<T, F> adapter;

    private final List<SMRule<F>> rules = new ArrayList<>();

    private String identifier;

    public SMSchemaFieldBuilder(SMSchemaBuilder<T> builder, SMAdapter<T, F> adapter) {
        this.builder = builder;
        this.adapter = adapter;
    }

    public SMSchemaFieldBuilder<T, F> setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public SMSchemaFieldBuilder<T, F> addRule(SMRule<F> rule) {
        this.rules.add(rule);

        return this;
    }

    public SMSchemaBuilder<T> build() {
        return this.builder.addField(new SMSchemaField<T, F>(this.identifier, this.adapter, this.rules));
    }
}
