package lt.bongibau.smapi.api.commands.validator.rule;

import lt.bongibau.smapi.api.commands.CommandContext;
import lt.bongibau.smapi.api.validator.rule.RuleValidationException;
import lt.bongibau.smapi.api.validator.rule.SMRule;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class PermittedRule implements SMRule<CommandContext> {

    private final List<@Nullable Permission> permissions;

    public PermittedRule(@Nullable Permission permissions) {
        this.permissions = Collections.singletonList(permissions);
    }

    public PermittedRule(@NotNull List<@Nullable Permission> permissions) {
        this.permissions = Collections.unmodifiableList(permissions);
    }

    @Override
    public void validate(@Nullable CommandContext context) throws RuleValidationException {
        if (context == null) return;

        for (Permission permission : permissions) {
            if (permission == null) continue;

            if (!context.getSender().hasPermission(permission)) {
                throw new RuleValidationException(this);
            }
        }
    }

    @Override
    public String identifier() {
        return "permitted";
    }
}
