package lt.bongibau.smapi.api.adapter.exception;

import org.jetbrains.annotations.NotNull;

public class AdapterSerializationException extends AdapterException {
    @Override
    @NotNull
    public String getIdentifier() {
        return "serialisation";
    }
}
