package lt.bongibau.smapi.api.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMCommandArgument {
    @NotNull
    private final String identifier;

    @Nullable
    private final String description;

    @Nullable
    private final String displayName;

    public SMCommandArgument(@NotNull String identifier, @Nullable String displayName) {
        this(identifier, displayName, null);
    }

    public SMCommandArgument(@NotNull String identifier, @Nullable String displayName, @Nullable String description) {
        this.identifier = identifier;
        this.description = description;
        this.displayName = displayName;
    }

    public @NotNull String getIdentifier() {
        return identifier;
    }

    public String getDisplayName() {
        return displayName == null ? identifier : displayName;
    }

    public @Nullable String getDescription() {
        return description;
    }
}
