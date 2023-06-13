package lt.bongibau.smapi.validator.rule;

import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class SupplierRule<T> implements SMRule<T> {

    private final Function<T, Boolean> supplier;

    private final String identifier;

    public SupplierRule(String identifier, Function<T, Boolean> supplier) {
        this.supplier = supplier;
        this.identifier = identifier;
    }

    @Override
    public void validate(@Nullable T value) throws RuleValidationException {
        Boolean result = this.supplier.apply(value);

        if (result == null || !result) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return identifier;
    }
}
