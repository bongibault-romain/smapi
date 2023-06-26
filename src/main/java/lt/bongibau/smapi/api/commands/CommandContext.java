package lt.bongibau.smapi.api.commands;

import lt.bongibau.smapi.api.validator.schema.SMSchemaContext;
import lt.bongibau.smapi.api.validator.schema.SMSchemaData;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandContext extends SMSchemaContext<String> {

    private final SMCommand command;

    private final String[] arguments;

    private final CommandSender sender;

    public CommandContext(SMCommand command, CommandSender sender, String[] arguments) {
        super(new ArrayList<>());

        this.command = command;
        this.arguments = arguments;
        this.sender = sender;

        List<SMSchemaData<String>> data = this.data;

        for (int i = 0; i < this.command.getArguments().size(); i++) {
            data.add(new SMSchemaData<>(this.command.getArguments().get(i).getIdentifier(), this.arguments[i]));
        }
    }

    public CommandSender getSender() {
        return sender;
    }

    public SMCommand getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }
}
