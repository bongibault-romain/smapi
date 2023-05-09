package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.validator.SMRule;

import java.util.List;

public class SMSchemaField<T, K> {

    private final String identifier;

    private final SMAdapter<T, K> adapter;

    private final List<SMRule<K>> rules;

    public SMSchemaField(String identifier, SMAdapter<T, K> adapter, List<SMRule<K>> rules) {
        this.identifier = identifier;
        this.adapter = adapter;
        this.rules = rules;
    }

    public String identifier() {
        return this.identifier;
    }

    public SMAdapter<T, K> adapter() {
        return this.adapter;
    }

    public List<SMRule<K>> rules() {
        return this.rules;
    }

    public void validate() {

    }
}
