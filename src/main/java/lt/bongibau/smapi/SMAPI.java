package lt.bongibau.smapi;

import lt.bongibau.smapi.adapter.AdapterRegistry;
import lt.bongibau.smapi.adapter.string.BooleanAdapter;
import lt.bongibau.smapi.adapter.string.IntegerAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import lt.bongibau.smapi.api.validator.schema.SMSchema;
import lt.bongibau.smapi.api.validator.schema.SMSchemaBuilder;
import lt.bongibau.smapi.api.validator.schema.SMSchemaData;
import lt.bongibau.smapi.api.validator.schema.SchemaValidationException;
import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.validator.rule.EqualsRule;
import lt.bongibau.smapi.validator.rule.MaximumRule;
import lt.bongibau.smapi.validator.rule.RequiredRule;
import lt.bongibau.smapi.validator.rule.SupplierRule;

import java.util.List;

public class SMAPI {
    public static void main(String[] args) throws SMRegistryLoadingException, AdapterSerializationException {
        AdapterRegistry.getInstance().load();

        SMSchema<String> schema = new SMSchemaBuilder<String>()
                .addField(AdapterRegistry.getInstance().get(IntegerAdapter.class))
                .setIdentifier("test")
                .addRule(new RequiredRule<>())
                .addRule(new EqualsRule<>(19))
                .addRule(new SupplierRule<>("fn1", integer -> {
                    return false;
                }))
                .addRule(new MaximumRule<>(19, false))
                .build()
                .addField("test2", AdapterRegistry.getInstance().get(BooleanAdapter.class))
                .addRule(new RequiredRule<>())
                .build()
                .build();

        try {
            schema.validate(List.of(
                    new SMSchemaData<>("test", "19")
            ));
        } catch (SchemaValidationException e) {
            for (SchemaFieldException error : e.getErrors()) {
                System.out.println(error.identifier());
            }
            return;
        }

        System.out.println(schema);
    }
}
