package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

public abstract class SMRegistry {

    private boolean loaded = false;

    public final void load() throws SMRegistryLoadingException {
        if (this.isLoaded()) throw new SMRegistryLoadingException("This registry is already enabled.");

        this.onLoad();
        this.setLoaded(true);
    }

    public final void unload() throws SMRegistryUnLoadingException {
        if (!this.isLoaded()) throw new SMRegistryUnLoadingException("This registry is already disabled.");

        this.onUnload();
        this.setLoaded(false);
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    protected abstract void onLoad() throws SMRegistryLoadingException;

    protected abstract void onUnload() throws SMRegistryUnLoadingException;
}
