package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaField;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaFieldBuilder;

import java.util.ArrayList;
import java.util.List;

public class SMSchemaBuilder<Entry, Context extends SMSchemaContext<Entry>> {

    protected final List<SMSchemaField<Entry, ?>> fields = new ArrayList<>();

    protected final List<SMRule<Context>> rules = new ArrayList<>();

    public <F> SMSchemaFieldBuilder<Entry, F, Context> addField(SMAdapter<Entry, F> adapter) {
        return new SMSchemaFieldBuilder<>(this, adapter);
    }

    public <F> SMSchemaFieldBuilder<Entry, F, Context> addField(String name, SMAdapter<Entry, F> adapter, List<SMRule<F>> rules) {
        return this.addField(adapter).setIdentifier(name).addRules(rules);
    }

    public <F> SMSchemaFieldBuilder<Entry, F, Context> addField(String name, SMAdapter<Entry, F> adapter) {
        return this.addField(adapter).setIdentifier(name);
    }

    public SMSchemaBuilder<Entry, Context> addRule(SMRule<Context> rule) {
        this.rules.add(rule);

        return this;
    }

    public SMSchema<Entry, Context> build() {
        return new SMSchema<>(this.fields, this.rules);
    }

    public <F> SMSchemaBuilder<Entry, Context> addField(SMSchemaField<Entry, F> field) {
        this.fields.add(field);

        return this;
    }

}
