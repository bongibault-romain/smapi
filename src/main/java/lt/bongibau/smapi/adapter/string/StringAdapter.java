package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.jetbrains.annotations.Nullable;

public class StringAdapter implements SMAdapter<String, String> {
    @Override
    public @Nullable String serialize(@Nullable String value) throws AdapterSerializationException {
        return value;
    }

    @Override
    public @Nullable String deserialize(@Nullable String value) throws AdapterDeserializationException {
        return value;
    }
}
