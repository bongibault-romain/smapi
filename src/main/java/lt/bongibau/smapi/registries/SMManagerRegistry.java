package lt.bongibau.smapi.registries;

import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

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
    protected void onLoad() throws SMRegistryLoadingException {
        for (SMManager SMManager : this.getData()) {
            this.load(SMManager, new ArrayList<>());
        }
    }

    @Override
    protected void onUnload() throws SMRegistryUnLoadingException {
        SMManager SMManager = this.loadingOrder.pollLast();

        while (SMManager != null) {
            SMManager.unload();

            SMManager = this.loadingOrder.pollLast();
        }

        this.loadingOrder.clear();
    }

    private void load(SMManager SMManager, List<SMManager> loaded) throws SMRegistryLoadingException {
        if (SMManager.isLoaded()) return;

        if (loaded.contains(SMManager))
            throw new SMRegistryLoadingException("Circular dependency detected: " + SMManager + ".");

        loaded.add(SMManager);

        if (SMManager.getClass().isAnnotationPresent(SMManagerInfo.class)) {
            SMManagerInfo SMManagerInfo = SMManager.getClass().getAnnotation(SMManagerInfo.class);

            for (Class<? extends SMManager> dependencyClass : SMManagerInfo.dependencies()) {
                SMManager dependency = this.get(dependencyClass);

                if (dependency == null) {
                    throw new SMRegistryLoadingException("Dependency does not exists: " + dependencyClass + " for " + SMManager + ".");
                }

                this.load(dependency, loaded);
            }
        }

        SMManager.load();
        this.loadingOrder.add(SMManager);
    }
}
