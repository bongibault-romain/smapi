package lt.bongibau.smapi.api.validator.schema.field;

import lt.bongibau.smapi.api.exception.IdentifiedException;
import lt.bongibau.smapi.api.exception.InitiatedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SchemaFieldException extends InitiatedException implements IdentifiedException {

    private final String ruleIdentifier;

    @Nullable
    private final String prefixIdentifier;

    public SchemaFieldException(@Nullable IdentifiedException initiator, @NotNull String ruleIdentifier, @Nullable String prefixIdentifier) {
        super(initiator);

        this.prefixIdentifier = prefixIdentifier;
        this.ruleIdentifier = ruleIdentifier;
    }

    @Override
    public @NotNull String getIdentifier() {
        IdentifiedException initiator = this.getInitiator();

        if (initiator == null) {

            if (prefixIdentifier == null) return this.getRuleIdentifier();

            return prefixIdentifier + "." + this.getRuleIdentifier();
        }

        String initiatorIdentifier = this.getInitiator().getIdentifier();

        if (initiatorIdentifier == null) {
            return this.getRuleIdentifier();
        }

        if (prefixIdentifier == null) {
            return initiatorIdentifier + "." + this.getRuleIdentifier();
        }

        return prefixIdentifier + "." + initiatorIdentifier + "." + this.getRuleIdentifier();
    }

    public String getRuleIdentifier() {
        return ruleIdentifier;
    }

    public String getPrefixIdentifier() {
        return prefixIdentifier;
    }
}
