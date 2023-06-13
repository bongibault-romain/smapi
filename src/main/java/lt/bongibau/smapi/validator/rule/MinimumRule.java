package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

public class MinimumRule<T extends Comparable<T>> implements SMRule<T> {

    private final T minimum;

    private final boolean exclusive;

    public MinimumRule(T minimum) {
        this(minimum, false);
    }

    public MinimumRule(T minimum, boolean exclusive) {
        this.minimum = minimum;
        this.exclusive = exclusive;
    }

    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        if (value == null) {
            return;
        }

        int comparedValue = value.compareTo(this.minimum);

        if (exclusive && comparedValue <= 0) {
            throw new RuleValidationException(this);
        }

        if (!exclusive && comparedValue < 0) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return "minimum";
    }
}
