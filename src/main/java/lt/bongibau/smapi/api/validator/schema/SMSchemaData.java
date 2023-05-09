package lt.bongibau.smapi.api.validator.schema;

import java.util.Map;

public class SMSchemaData<T> {

    private final Map<String, T> data;

    public SMSchemaData(Map<String, T> data) {
        this.data = data;
    }

    public T get(String identifier) {
        return this.data.get(identifier);
    }
}
