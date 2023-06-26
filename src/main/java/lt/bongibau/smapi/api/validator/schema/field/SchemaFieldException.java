package lt.bongibau.smapi.api.validator.schema.field;

import lt.bongibau.smapi.api.exception.IdentifiedException;
import lt.bongibau.smapi.api.exception.InitiatedException;
import org.jetbrains.annotations.NotNull;

public class SchemaFieldException extends InitiatedException implements IdentifiedException {

    private final String ruleIdentifier;

    public SchemaFieldException(IdentifiedException initiator, @NotNull String ruleIdentifier) {
        super(initiator);

        this.ruleIdentifier = ruleIdentifier;
    }

    @Override
    public @NotNull String identifier() {
        String initiatorIdentifier = this.getInitiator().identifier();

        if (initiatorIdentifier == null) {
            return this.getRuleIdentifier();
        }

        return initiatorIdentifier + "." + this.getRuleIdentifier();
    }

    public String getRuleIdentifier() {
        return ruleIdentifier;
    }
}
