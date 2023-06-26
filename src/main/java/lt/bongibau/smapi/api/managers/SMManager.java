package lt.bongibau.smapi.api.managers;

import lt.bongibau.smapi.SMPlugin;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryUnLoadingException;

public abstract class SMManager {

    private boolean loaded = false;

    public final void load(SMPlugin plugin) throws SMRegistryLoadingException {
        if (this.isLoaded()) throw new SMRegistryLoadingException("This registry is already enabled.");

        this.onLoad(plugin);
        this.setLoaded(true);
    }

    public final void unload(SMPlugin plugin) throws SMRegistryUnLoadingException {
        if (!this.isLoaded()) throw new SMRegistryUnLoadingException("This registry is already disabled.");

        this.onUnload(plugin);
        this.setLoaded(false);
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    protected abstract void onLoad(SMPlugin plugin) throws SMRegistryLoadingException;

    protected abstract void onUnload(SMPlugin plugin) throws SMRegistryUnLoadingException;
}
