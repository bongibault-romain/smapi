package lt.bongibau.smapi.api.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SMCommandArgument {
    @NotNull
    private final String name;

    @Nullable
    private final String description;

    public SMCommandArgument(@NotNull String name) {
        this(name, null);
    }

    public SMCommandArgument(@NotNull String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    public @NotNull String getName() {
        return name;
    }

    public @Nullable String getDescription() {
        return description;
    }
}
