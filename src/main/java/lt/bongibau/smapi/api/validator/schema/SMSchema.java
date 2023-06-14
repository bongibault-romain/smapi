package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.validator.schema.field.SMSchemaField;
import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SMSchema<T> {
    private final @NotNull List<SMSchemaField<T, ?>> fields;

    public SMSchema(@NotNull List<SMSchemaField<T, ?>> fields) {
        this.fields = fields;

        /*
         * Check if all field identifiers are unique
         */
        List<String> identifiers = new ArrayList<>(List.of());
        for (SMSchemaField<T, ?> field : this.fields) {
            if (identifiers.contains(field.identifier())) {
                throw new IllegalArgumentException("Field identifier must be unique, but found duplicate: " + field.identifier() + ". All the fields must have a different identifier.");
            }

            identifiers.add(field.identifier());
        }
    }

    public SMSchemaDataProvider validate(List<SMSchemaData<T>> data) throws SchemaValidationException {
        List<SchemaFieldException> errors = new ArrayList<>();
        List<SMSchemaData<?>> providedData = new ArrayList<>();
        int errorAmount = 0;

        for (SMSchemaField<T, ?> field : this.fields()) {
            SMSchemaData<T> dataEntry = data.stream()
                    .filter(d -> Objects.equals(d.identifier(), field.identifier()))
                    .findFirst()
                    .orElse(new SMSchemaData<>(field.identifier(), null));

            try {
                providedData.add(new SMSchemaData<>(field.identifier(), field.validate(dataEntry)));
            } catch (SchemaValidationException e) {
                errors.addAll(e.getErrors());
                errorAmount += e.getErrors().size();
            }
        }

        if (errorAmount != 0) {
            throw new SchemaValidationException(errors);
        }

        return new SMSchemaDataProvider(providedData);
    }

    public @NotNull List<SMSchemaField<T, ?>> fields() {
        return fields;
    }
}
