package lt.bongibau.smapi.api.commands.validator.rule;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

public class IntegrityRule implements SMRule<CommandContext> {
    @Override
    public void validate(@Nullable CommandContext context) throws RuleValidationException {
        if (context == null) return;

        if (context.getData().size() < context.getCommand().getRequiredArguments().size()) {
            throw new RuleValidationException(this);
        }

        for (int i = 0; i < context.getData().size(); i++) {
            boolean condition = i >= context.getCommand().getRequiredArguments().size();

            if (!context.getData().get(i).getIdentifier().equals((condition
                    ? context.getCommand().getOptionalArguments()
                    : context.getCommand().getRequiredArguments()).get(i - (condition ? context.getCommand().getRequiredArguments().size() : 0)).getIdentifier())) {
                throw new RuleValidationException(this);
            }
        }
    }

    @Override
    public String identifier() {
        return "integrity";
    }
}
