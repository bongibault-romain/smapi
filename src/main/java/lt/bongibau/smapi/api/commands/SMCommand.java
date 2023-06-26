package lt.bongibau.smapi.api.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SMCommand implements CommandExecutor {

    @NotNull
    private final List<SMCommand> subCommands = new ArrayList<>();

    @NotNull
    private final List<SMCommandArgument> arguments = new ArrayList<>();

    @Nullable
    private final SMCommand parent;

    public SMCommand(@Nullable SMCommand parent) {
        this.parent = parent;
    }

    public SMCommand() {
        this(null);
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (this.hasSubCommands() && strings.length > 0) {
            SMCommand subCommand = this.subCommands.stream().filter(c -> c.getIdentifier().equalsIgnoreCase(strings[0])).findFirst().orElse(null);

            if (subCommand == null) {
                // TODO: SEND USAGE
                commandSender.sendMessage("Subcommand not found (todo: send usage)");
                return true;
            }

            String[] newStrings = new String[strings.length - 1];
            System.arraycopy(strings, 1, newStrings, 0, strings.length - 1);

            subCommand.onCommand(commandSender, command, s, newStrings);
            return true;
        }

        CommandContext context = new CommandContext(this, commandSender, strings);
        this.execute(context);

        return true;
    }

    public final void addSubCommand(@NotNull SMCommand command) {
        if (command == this)
            throw new IllegalArgumentException("Cannot add self as subcommand");

        this.subCommands.add(command);
    }

    public final void addArgument(String identifier, String description, boolean required) {
    }

    @NotNull
    public abstract String getIdentifier();

    /**
     * @return null if no permission is required
     */
    @Nullable
    public Permission getPermission() {
        return null;
    }

    @Nullable
    public String getDescription() {
        return null;
    }

    @NotNull
    public String[] getAliases() {
        return new String[]{this.getIdentifier()};
    }

    public boolean isPlayerOnly() {
        return false;
    }

    @Nullable
    public final SMCommand getParent() {
        return parent;
    }

    @NotNull
    public final List<SMCommandArgument> getArguments() {
        return Collections.unmodifiableList(this.arguments);
    }

    @NotNull
    public final List<SMCommand> getSubCommands() {
        return Collections.unmodifiableList(this.subCommands);
    }

    public final boolean hasSubCommands() {
        return !this.subCommands.isEmpty();
    }

    public abstract void execute(@NotNull CommandContext context);
}
