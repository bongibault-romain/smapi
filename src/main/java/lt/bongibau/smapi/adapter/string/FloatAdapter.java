package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.jetbrains.annotations.Nullable;

public class FloatAdapter implements SMAdapter<String, Float> {
    @Override
    @Nullable
    public Float serialize(@Nullable String value) throws AdapterSerializationException {
        if (value == null) return null;

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new AdapterSerializationException();
        }
    }

    @Override
    @Nullable
    public String deserialize(@Nullable Float value) throws AdapterDeserializationException {
        if (value == null) return null;

        return value.toString();
    }
}
