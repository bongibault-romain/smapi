package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

public class RequiredRule<T> implements SMRule<T> {
    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        if (value == null) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return "required";
    }
}
