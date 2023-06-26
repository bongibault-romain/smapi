package lt.bongibau.smapi.api.commands.validator.rule;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PlayerHoldItemRule implements SMRule<CommandContext> {

    @Nullable
    private final ItemStack itemStack;

    private final boolean negation;

    public PlayerHoldItemRule(@Nullable ItemStack itemStack, boolean negation) {
        this.itemStack = itemStack;
        this.negation = negation;
    }

    public PlayerHoldItemRule(@Nullable ItemStack itemStack) {
        this(itemStack, false);
    }

    public PlayerHoldItemRule() {
        this(null, false);
    }

    @Override
    public void validate(@Nullable CommandContext value) throws RuleValidationException {
        if (value == null) return;

        if (!(value.getSender() instanceof Player player)) {
            if (negation) return;

            throw new RuleValidationException(this);
        }

        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand == null) {
            if (itemStack == null) {
                if (negation) return;

                throw new RuleValidationException(this);
            }

            if (negation) {
                throw new RuleValidationException(this);
            }

            return;
        }

        if (itemStack == null) {
            if (negation) return;

            throw new RuleValidationException(this);
        }

        if (itemInHand.isSimilar(itemStack) == negation) {
            throw new RuleValidationException(this);
        }
    }

    @Override
    public String identifier() {
        return null;
    }
}
