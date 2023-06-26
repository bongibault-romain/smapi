package lt.bongibau.smapi.api.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMCommandArgument {
    @NotNull
    private final String identifier;

    @Nullable
    private final String description;

    public SMCommandArgument(@NotNull String identifier) {
        this(identifier, null);
    }

    public SMCommandArgument(@NotNull String identifier, @Nullable String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public @NotNull String getIdentifier() {
        return identifier;
    }

    public @Nullable String getDescription() {
        return description;
    }
}
