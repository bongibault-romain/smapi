package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializingException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import org.jetbrains.annotations.Nullable;

public class DoubleAdapter implements SMAdapter<String, Double> {
    @Override
    public Double serialize(@Nullable String value) throws AdapterSerializingException {
        if (value == null) throw new AdapterSerializingException();

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new AdapterSerializingException();
        }
    }

    @Override
    public String deserialize(@Nullable Double value) throws AdapterDeserializingException {
        if (value == null) throw new AdapterDeserializingException();

        return value.toString();
    }
}
