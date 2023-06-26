package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IncludedRule<T> implements SMRule<T> {

    @NotNull
    private final T[] values;

    public IncludedRule(@NotNull T[] values) {
        this.values = values;
    }

    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        if (value == null) return;

        for (T t : this.values) {
            if (t.equals(value)) return;
        }

        throw new RuleValidationException(this);
    }

    @Override
    public String identifier() {
        return "included";
    }
}
