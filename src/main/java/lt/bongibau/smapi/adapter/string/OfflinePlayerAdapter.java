package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

public class OfflinePlayerAdapter implements SMAdapter<String, OfflinePlayer> {
    @Override
    @Nullable
    public OfflinePlayer serialize(@Nullable String value) throws AdapterSerializationException {
        if (value == null) return null;

        return Bukkit.getOfflinePlayer(value);
    }

    @Override
    @Nullable
    public String deserialize(@Nullable OfflinePlayer value) throws AdapterDeserializationException {
        if (value == null) return null;

        return value.toString();
    }
}
