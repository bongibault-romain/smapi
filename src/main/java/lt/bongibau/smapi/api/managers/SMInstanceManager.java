package lt.bongibau.smapi.api.managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SMInstanceManager<T> extends SMManager {

    private final List<T> data = new ArrayList<>();

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

    protected Collection<T> getModifiableData() {
        return this.data;
    }
}
