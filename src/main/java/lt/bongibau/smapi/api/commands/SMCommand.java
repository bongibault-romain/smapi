package lt.bongibau.smapi.api.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SMCommand implements CommandExecutor {

    List<SMCommand> subCommands = new ArrayList<>();

    List<SMCommandArgument> arguments = new ArrayList<>();

    public SMCommand() {

    }

    @Override
    public final boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        HashMap<SMCommandArgument, String> argumentsMap = new HashMap<>();

        for (int i = 0; i < this.arguments.size(); i++) {
            SMCommandArgument argument = this.arguments.get(i);
            argumentsMap.put(argument, strings[i]);
        }

        CommandContext context = new CommandContext(argumentsMap, commandSender);

        this.execute(context);

        return false;
    }

    public void addSubCommand(@NotNull SMCommand command) {
        if (command == this)
            throw new IllegalArgumentException("Cannot add self as subcommand");

        this.subCommands.add(command);
    }

    public void addArgument(String name, String description, boolean required) {
    }

    @NotNull
    public abstract String getName();

    @Nullable
    public abstract String getPermission();

    @Nullable
    public abstract String getDescription();

    @NotNull
    public abstract String[] getAliases();

    public abstract void execute(@NotNull CommandContext context);
}
