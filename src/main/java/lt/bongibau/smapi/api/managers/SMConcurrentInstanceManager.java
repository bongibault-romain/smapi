package lt.bongibau.smapi.api.managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class SMConcurrentInstanceManager<T> extends SMManager {

    private final ConcurrentLinkedQueue<T> data = new ConcurrentLinkedQueue<>();

    public void register(@NotNull T registry) {
        if (!this.data.contains(registry))
            this.data.add(registry);
    }

    public void register(@NotNull List<T> registries) {
        registries.forEach(this::register);
    }

    public void unregister(@NotNull T registry) {
        this.data.remove(registry);
    }

    @Nullable
    public <K extends T> K get(@NotNull Class<K> registryClass) {
        T registry = this.data.stream().filter(registryClass::isInstance).findFirst().orElse(null);

        if (registry != null) {
            return registryClass.cast(registry);
        }

        return null;
    }

    public Collection<T> getData() {
        return Collections.unmodifiableCollection(this.data);
    }

    protected ConcurrentLinkedQueue<T> getModifiableData() {
        return this.data;
    }
}
