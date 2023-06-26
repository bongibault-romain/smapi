package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.field.SMSchemaField;
import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SMSchema<T> {
    private final @NotNull List<SMSchemaField<T, ?>> fields;

    private final @NotNull List<SMRule<SMSchemaContext<T>>> rules;

    public SMSchema(@NotNull List<SMSchemaField<T, ?>> fields, @NotNull List<SMRule<SMSchemaContext<T>>> contextRules) {
        this.fields = fields;
        this.rules = contextRules;

        /*
         * Check if all field fieldIdentifiers are unique
         */
        List<String> fieldIdentifiers = new ArrayList<>();
        for (SMSchemaField<T, ?> field : this.fields) {
            if (fieldIdentifiers.contains(field.identifier())) {
                throw new IllegalArgumentException("Field identifier must be unique, but found duplicate: " + field.identifier() + ". All the fields must have a different identifier.");
            }

            fieldIdentifiers.add(field.identifier());
        }

        /*
         * Check if all rule ruleIdentifiers are unique
         */
        List<String> ruleIdentifiers = new ArrayList<>();
        for (SMRule<SMSchemaContext<T>> rule : this.rules) {
            if (ruleIdentifiers.contains(rule.identifier())) {
                throw new IllegalArgumentException("Rule identifier must be unique, but found duplicate: " + rule.identifier() + ".");
            }

            ruleIdentifiers.add(rule.identifier());
        }
    }

    public SMSchemaResult validate(SMSchemaContext<T> context) throws SchemaValidationException {
        return this.validate(context.getData());
    }

    public SMSchemaResult validate(List<SMSchemaData<T>> data) throws SchemaValidationException {
        List<SchemaFieldException> errors = new ArrayList<>();
        List<SMSchemaData<?>> result = new ArrayList<>();
        int errorAmount = 0;

        for (SMRule<SMSchemaContext<T>> rule : this.rules()) {
            try {
                rule.validate(new SMSchemaContext<T>(data));
            } catch (RuleValidationException e) {
                throw new RuntimeException(e);
            }
        }

        for (SMSchemaField<T, ?> field : this.fields()) {
            SMSchemaData<T> dataEntry = data.stream()
                    .filter(d -> Objects.equals(d.identifier(), field.identifier()))
                    .findFirst()
                    .orElse(new SMSchemaData<>(field.identifier(), null));

            try {
                result.add(new SMSchemaData<>(field.identifier(), field.validate(dataEntry)));
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

    public void addRule(SMRule<SMSchemaContext<T>> rule) {
        /*
         * Check if rule identifier is unique
         */
        for (SMRule<SMSchemaContext<T>> r : this.rules) {
            if (Objects.equals(r.identifier(), rule.identifier())) {
                throw new IllegalArgumentException("Rule identifier must be unique, but found duplicate: " + rule.identifier() + ".");
            }
        }

        this.rules.add(rule);
    }

    public @NotNull List<SMSchemaField<T, ?>> fields() {
        return fields;
    }

    public List<SMRule<SMSchemaContext<T>>> rules() {
        return rules;
    }
}
