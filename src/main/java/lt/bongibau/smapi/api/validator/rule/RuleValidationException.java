package lt.bongibau.smapi.api.validator.rule;

public class RuleValidationException extends Exception {
    private final SMRule<?> rule;

    public RuleValidationException(SMRule<?> rule) {
        this.rule = rule;
    }

    public SMRule<?> getRule() {
        return rule;
    }
}
