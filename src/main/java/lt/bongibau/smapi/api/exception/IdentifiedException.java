package lt.bongibau.smapi.api.exception;

import org.jetbrains.annotations.Nullable;

public interface IdentifiedException {
    @Nullable String getIdentifier();
}
