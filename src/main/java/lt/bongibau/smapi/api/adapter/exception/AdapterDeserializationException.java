package lt.bongibau.smapi.api.adapter.exception;

import org.jetbrains.annotations.NotNull;

public class AdapterDeserializationException extends AdapterException {
    @Override
    @NotNull
    public String identifier() {
        return "deserialization";
    }
}
