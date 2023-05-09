package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializingException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import org.jetbrains.annotations.Nullable;

public class BooleanAdapter implements SMAdapter<String, Boolean> {
    @Override
    public Boolean serialize(@Nullable String value) throws AdapterSerializingException {
        if (value == null) throw new AdapterSerializingException();

        return Boolean.parseBoolean(value);
    }

    @Override
    public String deserialize(@Nullable Boolean value) throws AdapterDeserializingException {
        if (value == null) throw new AdapterDeserializingException();

        return value.toString();
    }
}
