package lt.bongibau.smapi.api.validator.schema;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SMSchemaResult {
    private final List<SMSchemaData<?>> result;

    public SMSchemaResult(List<SMSchemaData<?>> result) {
        this.result = result;
    }

    public <T> T get(String identifier, Class<T> clazz) {
        SMSchemaData<?> data = this.result.stream().filter(d -> Objects.equals(d.getIdentifier(), identifier)).findFirst().orElse(null);

        if (data == null) return null;

        return clazz.cast(data.getValue());
    }

    public <T> T get(String identifier, Class<T> clazz, @NotNull T defaultValue) {
        T t = this.get(identifier, clazz);

        if (t == null) {
            return defaultValue;
        }

        return t;
    }


}
