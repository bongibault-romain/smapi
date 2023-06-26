package lt.bongibau.smapi.managers;

import lt.bongibau.smapi.SMPlugin;
import lt.bongibau.smapi.api.managers.SMInstanceManager;
import lt.bongibau.smapi.api.managers.SMManager;
import lt.bongibau.smapi.api.managers.SMManagerInfo;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryUnLoadingException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class SMManagerRegistry extends SMInstanceManager<SMManager> {

    private static final SMManagerRegistry instance = new SMManagerRegistry();

    private final Deque<SMManager> loadingOrder = new ArrayDeque<>();

    public static SMManagerRegistry getInstance() {
        return instance;
    }

    @Override
    public void onLoad(SMPlugin plugin) throws SMRegistryLoadingException {
        for (SMManager SMManager : this.getData()) {
            this.load(SMManager, new ArrayList<>(), plugin);
        }
    }

    @Override
    public void onUnload(SMPlugin plugin) throws SMRegistryUnLoadingException {
        SMManager manager = this.loadingOrder.pollLast();

        while (manager != null) {
            manager.unload(plugin);

            manager = this.loadingOrder.pollLast();
        }

        this.loadingOrder.clear();
    }

    private void load(SMManager manager, List<SMManager> loaded, SMPlugin plugin) throws SMRegistryLoadingException {
        if (manager.isLoaded()) return;

        if (loaded.contains(manager))
            throw new SMRegistryLoadingException("Circular dependency detected: " + manager + ".");

        loaded.add(manager);

        if (manager.getClass().isAnnotationPresent(SMManagerInfo.class)) {
            SMManagerInfo SMManagerInfo = manager.getClass().getAnnotation(SMManagerInfo.class);

            for (Class<? extends SMManager> dependencyClass : SMManagerInfo.dependencies()) {
                SMManager dependency = this.get(dependencyClass);

                if (dependency == null) {
                    throw new SMRegistryLoadingException("Dependency does not exists: " + dependencyClass + " for " + manager + ".");
                }

                this.load(dependency, loaded, plugin);
            }
        }

        plugin.getLogger().info("Loading " + manager.getClass().getSimpleName() + "...");
        manager.load(plugin);
        this.loadingOrder.add(manager);
    }
}
