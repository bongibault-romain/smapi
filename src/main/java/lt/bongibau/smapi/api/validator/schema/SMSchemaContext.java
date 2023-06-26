package lt.bongibau.smapi.api.validator.schema;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SMSchemaContext<T> {

    @NotNull
    protected final List<SMSchemaData<T>> data;

    public SMSchemaContext(@NotNull List<SMSchemaData<T>> data) {
        this.data = data;
    }

    public @NotNull SMSchemaData<T> get(String identifier) {
        SMSchemaData<T> stringSMSchemaData = this.data.stream().filter(d -> Objects.equals(d.getIdentifier(), identifier)).findFirst().orElse(null);

        if (stringSMSchemaData == null) {
            throw new IllegalArgumentException("No data found for identifier: " + identifier);
        }

        return stringSMSchemaData;
    }

    @NotNull
    public List<SMSchemaData<T>> getData() {
        return Collections.unmodifiableList(this.data);
    }


    @Nullable
    public String getIdentifierPrefix() {
        return null;
    }
}
