package lt.bongibau.smapi.api.validator.schema.field;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.SMSchemaBuilder;
import lt.bongibau.smapi.api.validator.schema.SMSchemaContext;

import java.util.ArrayList;
import java.util.List;

public class SMSchemaFieldBuilder<Entry, Result, Context extends SMSchemaContext<Entry>> {

    private final SMSchemaBuilder<Entry, Context> builder;

    private final SMAdapter<Entry, Result> adapter;

    private final List<SMRule<Result>> rules = new ArrayList<>();

    private String identifier;

    public SMSchemaFieldBuilder(SMSchemaBuilder<Entry, Context> builder, SMAdapter<Entry, Result> adapter) {
        this.builder = builder;
        this.adapter = adapter;
    }

    public SMSchemaFieldBuilder<Entry, Result, Context> setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public SMSchemaFieldBuilder<Entry, Result, Context> addRule(SMRule<Result> rule) {
        this.rules.add(rule);

        return this;
    }

    public SMSchemaBuilder<Entry, Context> build() {
        return this.builder.addField(new SMSchemaField<Entry, Result>(this.identifier, this.adapter, this.rules));
    }

    public SMSchemaFieldBuilder<Entry, Result, Context> addRules(List<SMRule<Result>> rules) {
        this.rules.addAll(rules);
        return this;
    }
}
