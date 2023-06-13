package lt.bongibau.smapi.api.validator.rule;

import org.jetbrains.annotations.Nullable;

public interface SMRule<T> {
    void validate(@Nullable T value) throws RuleValidationException;

    String identifier();
}
