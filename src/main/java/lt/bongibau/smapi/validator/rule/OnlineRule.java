package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class OnlineRule implements SMRule<Player> {
    @Override
    public void validate(@Nullable Player value) throws RuleValidationException {
        if (value == null) return;

        if (!value.isOnline()) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return null;
    }
}
