package lt.bongibau.smapi.registries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SMInstanceRegistry<T> extends SMRegistry {

    private final List<T> data = new ArrayList<>();

    public void register(@NotNull T registry) {
        if (!this.data.contains(registry))
            this.data.add(registry);
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

    public List<T> getData() {
        return Collections.unmodifiableList(this.data);
    }
}
