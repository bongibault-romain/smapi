package lt.bongibau.smapi.commands;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.commands.SMCommand;
import org.jetbrains.annotations.NotNull;

public class SubTestCommand extends SMCommand {

    @Override
    public void onLoad() {

    }

    @Override
    public @NotNull String getIdentifier() {
        return "subtest";
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        context.getSender().sendMessage("Subtest command executed!");
    }
}
