package lt.bongibau.smapi.api.exception;

import org.jetbrains.annotations.NotNull;

public interface IdentifiedException {
    @NotNull String identifier();
}
