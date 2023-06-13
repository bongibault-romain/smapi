package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.jetbrains.annotations.Nullable;

public class BooleanAdapter implements SMAdapter<String, Boolean> {
    @Override
    @Nullable
    public Boolean serialize(@Nullable String value) throws AdapterSerializationException {
        if (value == null) return null;

        return Boolean.parseBoolean(value);
    }

    @Override
    @Nullable
    public String deserialize(@Nullable Boolean value) throws AdapterDeserializationException {
        if (value == null) return null;

        return value.toString();
    }
}
