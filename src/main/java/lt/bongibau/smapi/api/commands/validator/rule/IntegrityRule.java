package lt.bongibau.smapi.api.commands.validator.rule;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.commands.SMCommand;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegrityRule implements SMRule<CommandContext> {

    @NotNull
    private final SMCommand command;

    public IntegrityRule(@NotNull SMCommand command) {
        this.command = command;
    }

    @Override
    public void validate(@Nullable CommandContext schemaContext) throws RuleValidationException {
        if (schemaContext == null) return;

        if (schemaContext.getData().size() != this.command.getArguments().size()) {
            throw new RuleValidationException(this);
        }

        for (int i = 0; i < schemaContext.getData().size(); i++) {
            if (!schemaContext.getData().get(i).identifier().equals(this.command.getArguments().get(i).getIdentifier())) {
                throw new RuleValidationException(this);
            }
        }
    }

    @Override
    public String identifier() {
        return "integrity";
    }
}
