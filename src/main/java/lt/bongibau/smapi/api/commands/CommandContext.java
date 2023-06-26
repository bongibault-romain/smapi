package lt.bongibau.smapi.api.commands;

import lt.bongibau.smapi.api.validator.schema.*;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandContext extends SMSchemaContext<String> {

    private final SMCommand command;

    private final String[] arguments;

    private final CommandSender sender;

    private final SMSchema<String, CommandContext> schema;

    private SMSchemaResult validationResult;

    public CommandContext(SMCommand command, CommandSender sender, String[] arguments, SMSchema<String, CommandContext> schema) {
        super(new ArrayList<>());
        this.command = command;
        this.arguments = arguments;
        this.sender = sender;
        this.schema = schema;

        int requiredArgumentsLength = this.command.getRequiredArguments().size();
        int optionalArgumentsLength = this.command.getOptionalArguments().size();

        List<SMCommandArgument> requiredArguments = this.command.getRequiredArguments();
        List<SMCommandArgument> optionalArguments = this.command.getOptionalArguments();

        for (int i = 0; i < (requiredArgumentsLength + optionalArgumentsLength); i++) {
            if (i >= this.arguments.length) break;

            this.data.add(new SMSchemaData<>((i >= requiredArgumentsLength ? optionalArguments : requiredArguments).get(i - (i >= requiredArgumentsLength ? requiredArgumentsLength : 0)).getIdentifier(), this.arguments[i]));
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

    public void validate() throws SchemaValidationException {
        this.validationResult = this.schema.validate(this);
    }

    public <T> T get(String identifier, Class<T> clazz) {
        return this.validationResult.get(identifier, clazz);
    }

    public <T> T get(String identifier, Class<T> clazz, @NotNull T defaultValue) {
        return this.validationResult.get(identifier, clazz, defaultValue);
    }

    public String getIdentifierPrefix() {
        return this.command.getGlobalIdentifier();
    }


}
