package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.jetbrains.annotations.Nullable;

public class DoubleAdapter implements SMAdapter<String, Double> {
    @Override
    @Nullable
    public Double serialize(@Nullable String value) throws AdapterSerializationException {
        if (value == null) return null;

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new AdapterSerializationException();
        }
    }

    @Override
    @Nullable
    public String deserialize(@Nullable Double value) throws AdapterDeserializationException {
        if (value == null) return null;

        return value.toString();
    }
}
