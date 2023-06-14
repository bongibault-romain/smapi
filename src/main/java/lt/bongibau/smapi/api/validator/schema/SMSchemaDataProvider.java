package lt.bongibau.smapi.api.validator.schema;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SMSchemaDataProvider {
    private final List<SMSchemaData<?>> data;

    public SMSchemaDataProvider(List<SMSchemaData<?>> data) {
        this.data = data;
    }

    @NotNull
    public <T> T get(String identifier, Class<T> clazz) {
        T result = clazz.cast(this.data.stream().filter(sdata -> Objects.equals(sdata.identifier(), identifier)).findFirst().orElse(null));

        if (result == null) {
            throw new IllegalArgumentException("This identifier does not exist in the schema: " + identifier + ". Please check if the identifier is correct.");
        }

        return result;
    }
}
