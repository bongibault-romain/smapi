package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializationException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializationException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerAdapter implements SMAdapter<String, Player> {
    @Override
    @Nullable
    public Player serialize(@Nullable String value) throws AdapterSerializationException {
        if (value == null) return null;

        return Bukkit.getPlayer(value);
    }

    @Override
    @Nullable
    public String deserialize(@Nullable Player value) throws AdapterDeserializationException {
        if (value == null) return null;

        return value.toString();
    }
}
