package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaField;
import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SMSchema<Entry, Context extends SMSchemaContext<Entry>> {
    private final @NotNull List<SMSchemaField<Entry, ?>> fields;

    private final List<SMRule<Context>> rules;

    public SMSchema(@NotNull List<SMSchemaField<Entry, ?>> fields, List<SMRule<Context>> contextRules) {
        this.fields = fields;
        this.rules = contextRules;

        /*
         * Check if all field fieldIdentifiers are unique
         */
        List<String> fieldIdentifiers = new ArrayList<>();
        for (SMSchemaField<Entry, ?> field : this.fields) {
            if (fieldIdentifiers.contains(field.getIdentifier())) {
                throw new IllegalArgumentException("Field identifier must be unique, but found duplicate: " + field.getIdentifier() + ". All the fields must have a different identifier.");
            }

            fieldIdentifiers.add(field.getIdentifier());
        }

        /*
         * Check if all rule ruleIdentifiers are unique
         */
        List<String> ruleIdentifiers = new ArrayList<>();
        for (SMRule<Context> rule : this.rules) {
            if (ruleIdentifiers.contains(rule.identifier())) {
                throw new IllegalArgumentException("Rule identifier must be unique, but found duplicate: " + rule.identifier() + ".");
            }

            ruleIdentifiers.add(rule.identifier());
        }
    }

    public SMSchemaResult validate(Context context) throws SchemaValidationException {
        List<SchemaFieldException> errors = new ArrayList<>();
        List<SMSchemaData<?>> result = new ArrayList<>();
        int errorAmount = 0;

        for (SMRule<Context> rule : this.rules()) {
            try {
                rule.validate(context);
            } catch (RuleValidationException e) {
                errors.add(new SchemaFieldException(null, rule.identifier(), context.getIdentifierPrefix()));
                errorAmount++;
            }
        }

        List<SMSchemaData<Entry>> data = context.getData();

        for (SMSchemaField<Entry, ?> field : this.fields()) {
            SMSchemaData<Entry> dataEntry = data.stream()
                    .filter(d -> Objects.equals(d.getIdentifier(), field.getIdentifier()))
                    .findFirst()
                    .orElse(new SMSchemaData<>(field.getIdentifier(), null));

            try {
                result.add(new SMSchemaData<>(field.getIdentifier(), field.validate(dataEntry, context)));
            } catch (SchemaValidationException e) {
                errors.addAll(e.getErrors());
                errorAmount += e.getErrors().size();
            }
        }

        if (errorAmount != 0) {

            throw new SchemaValidationException(errors);
        }

        return new SMSchemaResult(result);
    }

    public void addRule(SMRule<Context> rule) {
        /*
         * Check if rule identifier is unique
         */
        for (SMRule<Context> r : this.rules) {
            if (Objects.equals(r.identifier(), rule.identifier())) {
                throw new IllegalArgumentException("Rule identifier must be unique, but found duplicate: " + rule.identifier() + ".");
            }
        }

        this.rules.add(rule);
    }

    public @NotNull List<SMSchemaField<Entry, ?>> fields() {
        return fields;
    }

    public List<SMRule<Context>> rules() {
        return rules;
    }
}
