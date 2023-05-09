package lt.bongibau.smapi.api.adapter;

import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializingException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import org.jetbrains.annotations.Nullable;

public interface SMAdapter<Entry, Result> {
    Result serialize(@Nullable Entry value) throws AdapterSerializingException;

    Entry deserialize(@Nullable Result value) throws AdapterDeserializingException;
}
