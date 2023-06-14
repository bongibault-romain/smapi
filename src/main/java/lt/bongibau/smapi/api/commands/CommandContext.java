package lt.bongibau.smapi.api.commands;

import lt.bongibau.smapi.api.validator.schema.SMSchema;
import lt.bongibau.smapi.api.validator.schema.SMSchemaData;
import lt.bongibau.smapi.api.validator.schema.SMSchemaDataProvider;
import lt.bongibau.smapi.api.validator.schema.SchemaValidationException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class CommandContext {

    private final HashMap<SMCommandArgument, String> arguments;

    private final CommandSender sender;

    public CommandContext(HashMap<SMCommandArgument, String> arguments, @NotNull CommandSender commandSender) {
        this.arguments = arguments;
        this.sender = commandSender;
    }

    public SMSchemaDataProvider validate(SMSchema<String> schema) throws SchemaValidationException {
        return schema.validate(this.getSchemaData());
    }

    public CommandSender getSender() {
        return sender;
    }

    // TODO: change to SMSchemaDataProvider or create a new class more suitable for this purpose
    private List<SMSchemaData<String>> getSchemaData() {
        return this.arguments.entrySet().stream().map(entry -> new SMSchemaData<>(entry.getKey().getName(), entry.getValue())).toList();
    }
}
