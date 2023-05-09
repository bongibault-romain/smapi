package lt.bongibau.smapi;

import lt.bongibau.smapi.adapter.AdapterRegistry;
import lt.bongibau.smapi.adapter.string.PlayerAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import lt.bongibau.smapi.api.validator.schema.SMSchema;
import lt.bongibau.smapi.api.validator.schema.SMSchemaBuilder;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;

public class SMAPI {
    public static void main(String[] args) throws SMRegistryLoadingException, AdapterSerializingException {
        AdapterRegistry.getInstance().load();

        SMSchema<String> schema = new SMSchemaBuilder<String>()
                .addField(AdapterRegistry.getInstance().get(PlayerAdapter.class))
                .build()
                .build();

        schema.validate();

        System.out.println(schema);
    }
}
