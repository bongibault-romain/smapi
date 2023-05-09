package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class SMRegistryManager extends InstanceSMRegistry<SMRegistry> {

    private static final SMRegistryManager instance = new SMRegistryManager();

    private final Deque<SMRegistry> loadingOrder = new ArrayDeque<>();

    public static SMRegistryManager getInstance() {
        return instance;
    }

    @Override
    protected void onLoad() throws SMRegistryLoadingException {
        for (SMRegistry SMRegistry : this.getData()) {
            this.load(SMRegistry, new ArrayList<>());
        }
    }

    @Override
    protected void onUnload() throws SMRegistryUnLoadingException {
        SMRegistry SMRegistry = this.loadingOrder.pollLast();

        while (SMRegistry != null) {
            SMRegistry.unload();

            SMRegistry = this.loadingOrder.pollLast();
        }

        this.loadingOrder.clear();
    }

    private void load(SMRegistry SMRegistry, List<SMRegistry> loaded) throws SMRegistryLoadingException {
        if (SMRegistry.isLoaded()) return;

        if (loaded.contains(SMRegistry))
            throw new SMRegistryLoadingException("Circular dependency detected: " + SMRegistry + ".");

        loaded.add(SMRegistry);

        if (SMRegistry.getClass().isAnnotationPresent(SMRegistryInfo.class)) {
            SMRegistryInfo SMRegistryInfo = SMRegistry.getClass().getAnnotation(SMRegistryInfo.class);

            for (Class<? extends SMRegistry> dependencyClass : SMRegistryInfo.dependencies()) {
                SMRegistry dependency = this.get(dependencyClass);

                if (dependency == null) {
                    throw new SMRegistryLoadingException("Dependency does not exists: " + dependencyClass + " for " + SMRegistry + ".");
                }

                this.load(dependency, loaded);
            }
        }

        SMRegistry.load();
        this.loadingOrder.add(SMRegistry);
    }
}
