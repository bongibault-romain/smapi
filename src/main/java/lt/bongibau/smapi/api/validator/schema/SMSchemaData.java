package lt.bongibau.smapi.api.validator.schema;

import lt.bongibau.smapi.api.exception.IdentifiedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMSchemaData<T> implements IdentifiedException {

    private final String identifier;

    @Nullable
    private final T value;

    public SMSchemaData(String identifier, @Nullable T value) {
        this.identifier = identifier;
        this.value = value;
    }

    @Nullable
    public T value() {
        return this.value;
    }

    @Override
    public @NotNull String identifier() {
        return this.identifier;
    }
}
