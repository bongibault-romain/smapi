package lt.bongibau.smapi.api.commands.validator.rule;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerOnlyRule implements SMRule<CommandContext> {
    @Override
    public void validate(@Nullable CommandContext context) throws RuleValidationException {
        if (context == null) return;

        if (context.getSender() == null || !(context.getSender() instanceof Player)) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return "player-only";
    }
}
