package lt.bongibau.smapi.api.validator;

import lt.bongibau.smapi.api.validator.exception.RuleValidationException;

public interface SMRule<T> {
    void validate(T value) throws RuleValidationException;
}
