package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

public abstract class SMRegistry {

    private boolean enabled = false;

    public final void enable() throws SMRegistryLoadingException {
        if (this.isEnabled()) throw new SMRegistryLoadingException("This registry is already enabled.");

        this.setEnabled(true);
        this.onEnable();
    }

    public final void disable() throws SMRegistryUnLoadingException {
        if (!this.isEnabled()) throw new SMRegistryUnLoadingException("This registry is already disabled.");

        this.setEnabled(false);
        this.onDisable();
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    private void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected abstract void onEnable() throws SMRegistryLoadingException;

    protected abstract void onDisable() throws SMRegistryUnLoadingException;
}
