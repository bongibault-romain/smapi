package lt.bongibau.smapi.api.commands;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.commands.validator.rule.IntegrityRule;
import lt.bongibau.smapi.api.commands.validator.rule.PermittedRule;
import lt.bongibau.smapi.api.commands.validator.rule.PlayerOnlyRule;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import lt.bongibau.smapi.api.validator.schema.SMSchemaBuilder;
import lt.bongibau.smapi.api.validator.schema.SchemaValidationException;
import lt.bongibau.smapi.api.validator.schema.field.SchemaFieldException;
import lt.bongibau.smapi.validator.rule.RequiredRule;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SMCommand implements CommandExecutor, TabCompleter {

    @NotNull
    private final List<SMCommand> subCommands = new ArrayList<>();

    @NotNull
    private final List<SMCommandArgument> requiredArguments = new ArrayList<>();

    @NotNull
    private final List<SMCommandArgument> optionalArguments = new ArrayList<>();
    private final SMSchemaBuilder<String, CommandContext> schemaBuilder = new SMSchemaBuilder<>();
    @Nullable
    private SMCommand parent;

    public SMCommand() {
        this.schemaBuilder
                .addRule(new PermittedRule(this.getPermission()))
                .addRule(new IntegrityRule());

        if (this.isPlayerOnly()) {
            this.schemaBuilder.addRule(new PlayerOnlyRule());
        }
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (this.hasSubCommands() && strings.length > 0) {
            SMCommand subCommand = this.subCommands.stream().filter(c -> c.getIdentifier().equalsIgnoreCase(strings[0])).findFirst().orElse(null);

            if (subCommand == null) {
                for (TextComponent component : this.getUsage()) {
                    if (this.isPlayerOnly()) {
                        ((Player) commandSender).spigot().sendMessage(component);
                    } else {
                        commandSender.sendMessage(component.toPlainText());
                    }
                }
                return true;
            }

            String[] newStrings = new String[strings.length - 1];
            System.arraycopy(strings, 1, newStrings, 0, strings.length - 1);

            subCommand.onCommand(commandSender, command, s, newStrings);
            return true;
        }


        CommandContext context = new CommandContext(this, commandSender, strings, this.schemaBuilder.build());

        try {
            context.validate();
        } catch (SchemaValidationException e) {
            List<SchemaFieldException> errors = e.getErrors();

            if (errors.size() > 0) {
                commandSender.sendMessage(this.getMessage(errors.get(0).getIdentifier()));
                return true;
            }
        }

        this.execute(context);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Collections.emptyList();
    }

    public final void addSubCommand(@NotNull SMCommand command) {
        if (this.getRequiredArguments().size() > 0 || this.getOptionalArguments().size() > 0) {
            throw new IllegalCallerException("Cannot add subcommand to command with arguments");
        }

        if (command == this)
            throw new IllegalArgumentException("Cannot add self as subcommand");

        command.setParent(this);
        command.onLoad();

        this.subCommands.add(command);
    }

    public final <T> void addArgument(String identifier, SMAdapter<String, T> adapter, List<SMRule<T>> rules) {
        this.addArgument(identifier, null, null, adapter, rules);
    }

    public final <T> void addArgument(String identifier, @Nullable String displayName, SMAdapter<String, T> adapter, List<SMRule<T>> rules) {
        this.addArgument(identifier, displayName, null, adapter, rules);
    }

    public final <T> void addArgument(String identifier, @Nullable String displayName, @Nullable String description, SMAdapter<String, T> adapter, List<SMRule<T>> rules) {
        if (this.hasSubCommands()) {
            throw new IllegalCallerException("Cannot add argument to command with subcommands");
        }

        boolean isRequired = rules.stream().anyMatch(r -> r instanceof RequiredRule<T>);

        if (isRequired) {
            this.requiredArguments.add(
                    new SMCommandArgument(identifier, displayName, description)
            );
        } else {
            this.optionalArguments.add(
                    new SMCommandArgument(identifier, displayName, description)
            );
        }


        this.schemaBuilder
                .addField(identifier, adapter, rules)
                .build();
    }

    public void onLoad() {
    }

    public void onUnload() {
    }

    @NotNull
    public abstract String getIdentifier();

    public final String getGlobalIdentifier() {
        SMCommand parent = this.getParent();

        if (parent == null) {
            return this.getIdentifier();
        }

        return parent.getGlobalIdentifier() + "." + this.getIdentifier();
    }

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

    private final void setParent(@Nullable SMCommand parent) {
        this.parent = parent;
    }

    @NotNull
    public final List<SMCommandArgument> getOptionalArguments() {
        return Collections.unmodifiableList(this.optionalArguments);
    }

    @NotNull
    public final List<SMCommandArgument> getRequiredArguments() {
        return Collections.unmodifiableList(this.requiredArguments);
    }

    public List<TextComponent> getUsage() {
        return this.getCommandUsage();
    }

    public TextComponent getParentTextComponentUsage() {
        if (this.parent == null) {
            return new TextComponent("§e/" + this.getIdentifier());
        }

        TextComponent component = this.parent.getParentTextComponentUsage();
        component.addExtra(" ");
        component.addExtra(new TextComponent(this.getIdentifier()));

        return component;
    }

    public List<TextComponent> getCommandUsage() {
        List<TextComponent> components = new ArrayList<>();
        TextComponent parentComponent = this.getParentTextComponentUsage();

        if (this.hasSubCommands()) {

        }

        return components;
    }

    public TextComponent getRequiredArgumentUsage(SMCommandArgument argument) {
        TextComponent component = new TextComponent("§e<" + argument.getDisplayName() + ">");

        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent("§7" + argument.getDescription())
        }));

        return component;
    }

    public TextComponent getOptionalArgumentUsage(SMCommandArgument argument) {
        TextComponent component = new TextComponent("§e[" + argument.getDisplayName() + "]");

        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent("§7" + argument.getDescription())
        }));

        return component;
    }

    public void addRule(SMRule<CommandContext> rule) {
        this.schemaBuilder.addRule(rule);
    }

    @NotNull
    public final List<SMCommand> getSubCommands() {
        return Collections.unmodifiableList(this.subCommands);
    }

    public final boolean hasSubCommands() {
        return !this.subCommands.isEmpty();
    }

    public String getMessage(String path) {
        return path;
    }

    public abstract void execute(@NotNull CommandContext context);
}
