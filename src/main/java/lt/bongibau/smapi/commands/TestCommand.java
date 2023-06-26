package lt.bongibau.smapi.commands;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.commands.SMCommand;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends SMCommand {

    @Override
    public void onLoad() {
        this.addSubCommand(new SubTestCommand());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "test2";
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        context.getSender().sendMessage("cc");
    }
}
