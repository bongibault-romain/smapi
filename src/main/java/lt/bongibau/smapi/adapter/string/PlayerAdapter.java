package lt.bongibau.smapi.adapter.string;

import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.adapter.exception.AdapterDeserializingException;
import lt.bongibau.smapi.api.adapter.exception.AdapterSerializingException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerAdapter implements SMAdapter<String, Player> {
    @Override
    public Player serialize(@Nullable String value) throws AdapterSerializingException {
        if (value == null) throw new AdapterSerializingException();

        return Bukkit.getPlayer(value);
    }

    @Override
    public String deserialize(@Nullable Player value) throws AdapterDeserializingException {
        if (value == null) throw new AdapterDeserializingException();

        return value.toString();
    }
}
