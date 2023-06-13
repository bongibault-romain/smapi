package lt.bongibau.smapi.api.validator.schema.field;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import lt.bongibau.smapi.api.exception.IdentifiedException;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.SMSchemaData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record SMSchemaField<T, K>(@NotNull String identifier, SMAdapter<T, K> adapter,
                                  List<SMRule<K>> rules) implements IdentifiedException {
    public SMSchemaField(@NotNull String identifier, SMAdapter<T, K> adapter,
                         List<SMRule<K>> rules) {
        this.identifier = identifier.toLowerCase();
        this.adapter = adapter;
        this.rules = rules;

        /*
         * Check if all rule identifiers are unique
         */
        List<String> identifiers = new ArrayList<>(List.of());
        for (SMRule<K> rule : this.rules) {
            if (identifiers.contains(rule.identifier())) {
                throw new IllegalArgumentException("Rule identifier must be unique, but found duplicate: " + rule.identifier() + ". You may added same rule twice in the field " + this.identifier + ".");
            }

            identifiers.add(rule.identifier());
        }
    }

    public List<SchemaFieldException> validate(@NotNull SMSchemaData<T> data) {
        List<SchemaFieldException> errors = new ArrayList<>(List.of());

        try {
            K value = this.adapter.serialize(data.value());

            for (SMRule<K> rule : this.rules) {
                try {
                    rule.validate(value);
                } catch (RuleValidationException e) {
                    errors.add(new SchemaFieldException(this, rule.identifier()));
                }
            }
        } catch (AdapterSerializationException e) {
            return List.of(new SchemaFieldException(this, e.identifier()));
        }

        return errors;
    }
}
