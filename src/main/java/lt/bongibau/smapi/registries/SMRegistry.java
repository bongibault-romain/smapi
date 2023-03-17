package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

public abstract class SMRegistry {

    public boolean enabled = false;

    public final void enable() throws SMRegistryLoadingException {
        if (this.isEnabled()) throw new SMRegistryLoadingException("This registry is already enabled.");

        this.enabled = true;
        this.onEnable();
    }

    public final void disable() throws SMRegistryUnLoadingException {
        if (this.isEnabled()) throw new SMRegistryUnLoadingException("This registry is already disabled.");

        this.enabled = false;
        this.onDisable();
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();
}
