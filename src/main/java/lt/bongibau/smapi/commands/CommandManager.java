package lt.bongibau.smapi.commands;

import lt.bongibau.smapi.api.commands.SMCommand;
import lt.bongibau.smapi.registries.SMInstanceManager;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

public class CommandManager extends SMInstanceManager<SMCommand> {

    private static final CommandManager instance = new CommandManager();

    public static CommandManager getInstance() {
        return instance;
    }

    @Override
    protected void onLoad() throws SMRegistryLoadingException {
        for (SMCommand data : this.getData()) {
            Permission permission = data.getPermission();
            if (permission == null) continue;

            Bukkit.getPluginManager().addPermission(data.getPermission());
        }
    }

    @Override
    protected void onUnload() throws SMRegistryUnLoadingException {

    }
}
