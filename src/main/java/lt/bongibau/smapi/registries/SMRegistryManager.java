package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

import java.util.ArrayList;
import java.util.List;

public class SMRegistryManager extends SMRegistry {

    private static final SMRegistryManager instance = new SMRegistryManager();

    private final List<SMRegistry> registries = new ArrayList<>();

    @Override
    public void onEnable() throws SMRegistryLoadingException {
        for (SMRegistry registry : this.registries) {
            if (!registry.isEnabled()) registry.enable();
        }
    }

    @Override
    public void onDisable() throws SMRegistryUnLoadingException {
        for (SMRegistry registry : this.registries) {
            if (registry.isEnabled()) registry.disable();
        }

        this.registries.clear();
    }

    public void register(SMRegistry registry) {
        if (!this.registries.contains(registry))
            this.registries.add(registry);
    }

    public void unregister(SMRegistry registry) {
        this.registries.remove(registry);
    }

    public static SMRegistryManager getInstance() {
        return instance;
    }
}
