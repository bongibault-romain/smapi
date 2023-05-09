package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializingException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import org.jetbrains.annotations.Nullable;

public class IntegerAdapter implements SMAdapter<String, Integer> {
    @Override
    public Integer serialize(@Nullable String value) throws AdapterSerializingException {
        if (value == null) throw new AdapterSerializingException();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new AdapterSerializingException();
        }
    }

    @Override
    public String deserialize(@Nullable Integer value) throws AdapterDeserializingException {
        if (value == null) throw new AdapterDeserializingException();

        return value.toString();
    }
}
