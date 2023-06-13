package lt.bongibau.smapi.api.adapter;

import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.jetbrains.annotations.Nullable;

public interface SMAdapter<Entry, Result> {
    @Nullable
    Result serialize(@Nullable Entry value) throws AdapterSerializationException;

    @Nullable
    Entry deserialize(@Nullable Result value) throws AdapterDeserializationException;
}
