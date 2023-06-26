package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

public class EqualsRule<T> implements SMRule<T> {

    private final @Nullable T equalsTo;

    public EqualsRule(@Nullable T equalsTo) {
        this.equalsTo = equalsTo;
    }

    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        if (value == null && this.equalsTo == null) {
            return;
        }

        /*
         * If one of the values is null, but not both, then the rule is not satisfied.
         */
        if (value == null || this.equalsTo == null) {
            throw new RuleValidationException(this);
        }

        if (!value.equals(this.equalsTo)) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return "equals";
    }
}
