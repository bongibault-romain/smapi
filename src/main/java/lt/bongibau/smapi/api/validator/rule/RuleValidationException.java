package lt.bongibau.smapi.api.validator.rule;

import lt.bongibau.smapi.api.exception.IdentifiedException;
import org.jetbrains.annotations.Nullable;

public class RuleValidationException extends Exception implements IdentifiedException {
    private final SMRule<?> rule;

    public RuleValidationException(SMRule<?> rule) {
        this.rule = rule;
    }

    public SMRule<?> getRule() {
        return rule;
    }

    @Override
    public @Nullable String getIdentifier() {
        return rule.identifier();
    }
}
