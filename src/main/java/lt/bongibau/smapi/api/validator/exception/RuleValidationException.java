package lt.bongibau.smapi.api.validator.exception;

import lt.bongibau.smapi.api.validator.SMRule;

import java.util.Locale;

public abstract class RuleValidationException extends Exception {

    private final SMRule<?> rule;

    public RuleValidationException(SMRule<?> rule) {
        this.rule = rule;
    }

    public SMRule<?> getRule() {
        return rule;
    }

    public String getMessage() {
        return this.getMessage(Locale.getDefault());
    }

    public abstract String getMessage(Locale locale);
}
