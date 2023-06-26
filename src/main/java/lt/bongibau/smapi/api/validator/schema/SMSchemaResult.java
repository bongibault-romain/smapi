package lt.bongibau.smapi.api.validator.schema;

import java.util.List;
import java.util.Objects;

public class SMSchemaResult {
    private final List<SMSchemaData<?>> result;

    public SMSchemaResult(List<SMSchemaData<?>> result) {
        this.result = result;
    }

    public <T> T get(String identifier, Class<T> clazz) {
        return clazz.cast(this.result.stream().filter(data -> Objects.equals(data.identifier(), identifier)).findFirst().orElse(null));
    }

}
