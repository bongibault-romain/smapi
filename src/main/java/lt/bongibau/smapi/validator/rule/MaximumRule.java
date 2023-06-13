package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

public class MaximumRule<T extends Comparable<T>> implements SMRule<T> {

    private final T maximum;

    private final boolean exclusive;

    public MaximumRule(T maximum) {
        this(maximum, false);
    }

    public MaximumRule(T maximum, boolean exclusive) {
        this.maximum = maximum;
        this.exclusive = exclusive;
    }

    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        if (value == null) {
            return;
        }

        int comparedValue = value.compareTo(this.maximum);

        if (exclusive && comparedValue >= 0) {
            throw new RuleValidationException(this);
        }

        if (!exclusive && comparedValue > 0) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return "maximum";
    }
}
