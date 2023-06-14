package lt.bongibau.smapi.api.validator.schema;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SchemaDataProvider {

    @NotNull
    private final HashMap<String, ?> data;

    public SchemaDataProvider(@NotNull HashMap<String, ?> data) {
        this.data = data;
    }

    public <T> T get(@NotNull String identifier, @NotNull Class<T> clazz) {
        return clazz.cast(this.data.get(identifier));
    }
}
