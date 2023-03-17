package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@RegistryInfo(dependencies = SMRegistryManager.class)
public final class SMRegistryManager extends SMDataRegistry<SMRegistry> {

    private static final SMRegistryManager instance = new SMRegistryManager();

    private final Deque<SMRegistry> loadingOrder = new ArrayDeque<>();

    public static SMRegistryManager getInstance() {
        return instance;
    }

    @Override
    protected void onEnable() throws SMRegistryLoadingException {
        for (SMRegistry registry : this.getData()) {
            this.enable(registry, new ArrayList<>());
        }
    }

    @Override
    protected void onDisable() throws SMRegistryUnLoadingException {
        SMRegistry registry = this.loadingOrder.pollLast();

        while (registry != null) {
            registry.disable();

            registry = this.loadingOrder.pollLast();
        }

        this.loadingOrder.clear();
    }

    private void enable(SMRegistry registry, List<SMRegistry> loaded) throws SMRegistryLoadingException {
        if (registry.isEnabled()) return;

        if (loaded.contains(registry))
            throw new SMRegistryLoadingException("Circular dependency detected: " + registry + ".");

        loaded.add(registry);

        if (registry.getClass().isAnnotationPresent(RegistryInfo.class)) {
            RegistryInfo registryInfo = registry.getClass().getAnnotation(RegistryInfo.class);

            for (Class<? extends SMRegistry> dependencyClass : registryInfo.dependencies()) {
                SMRegistry dependency = this.get(dependencyClass);

                if (dependency == null) {
                    throw new SMRegistryLoadingException("Dependency does not exists: " + dependencyClass + " for " + registry + ".");
                }

                this.enable(dependency, loaded);
            }
        }

        registry.enable();
        this.loadingOrder.add(registry);
    }
}
