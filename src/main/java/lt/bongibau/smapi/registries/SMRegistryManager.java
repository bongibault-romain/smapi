package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RegistryInfo(dependencies = SMRegistryManager.class)
public final class SMRegistryManager extends SMRegistry {

    private static final SMRegistryManager instance = new SMRegistryManager();

    private final List<SMRegistry> registries = new ArrayList<>();

    @Override
    public void onEnable() throws SMRegistryLoadingException {
        for (SMRegistry registry : this.registries) {
            this.enable(registry, new ArrayList<>());
        }
    }

    @Override
    public void onDisable() throws SMRegistryUnLoadingException {
        for (SMRegistry registry : this.registries) {
            if (registry.isEnabled()) registry.disable();
        }
    }

    public void register(@NotNull SMRegistry registry) {
        if (!this.registries.contains(registry))
            this.registries.add(registry);
    }

    public void unregister(@NotNull SMRegistry registry) {
        this.registries.remove(registry);
    }

    @Nullable
    public <T extends SMRegistry> T get(@NotNull Class<T> registryClass) {
        SMRegistry registry = this.registries.stream().filter(registryClass::isInstance).findFirst().orElse(null);

        if (registry != null) {
            return registryClass.cast(registry);
        }

        return null;
    }

    public static SMRegistryManager getInstance() {
        return instance;
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

            registry.enable();
        }
    }

}
