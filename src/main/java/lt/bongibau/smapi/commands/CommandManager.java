package lt.bongibau.smapi.commands;

import lt.bongibau.smapi.SMPlugin;
import lt.bongibau.smapi.adapter.AdapterManager;
import lt.bongibau.smapi.api.commands.SMCommand;
import lt.bongibau.smapi.api.managers.SMInstanceManager;
import lt.bongibau.smapi.api.managers.SMManagerInfo;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryUnLoadingException;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;

import java.util.List;

@SMManagerInfo(dependencies = {AdapterManager.class})
public class CommandManager extends SMInstanceManager<SMCommand> {

    private static final CommandManager instance = new CommandManager();

    public static CommandManager getInstance() {
        return instance;
    }

    @Override
    protected void onLoad(SMPlugin plugin) throws SMRegistryLoadingException {
        for (SMCommand data : this.getData()) {
            Permission permission = data.getPermission();
            if (permission != null) {
                plugin.getServer().getPluginManager().addPermission(data.getPermission());
            }

            PluginCommand command = plugin.getCommand(data.getIdentifier());

            if (command == null) {
                plugin.getLogger().warning("Command '" + data.getIdentifier() + "' is not registered in plugin.yml.");
                continue;
            }

            command.setExecutor(data);
            command.setAliases(List.of(data.getAliases()));
            command.setTabCompleter(data);

            String description = data.getDescription();
            if (description != null) {
                command.setDescription(description);
            }

            command.setName(data.getIdentifier());

            data.onLoad();
        }
    }

    @Override
    protected void onUnload(SMPlugin plugin) throws SMRegistryUnLoadingException {
        for (SMCommand data : this.getData()) {
            data.onUnload();

            Permission permission = data.getPermission();
            if (permission == null) continue;

            plugin.getServer().getPluginManager().removePermission(data.getPermission());

            PluginCommand command = plugin.getCommand(data.getIdentifier());

            if (command == null) {
                plugin.getLogger().warning("Command '" + data.getIdentifier() + "' is not registered in plugin.yml.");
                continue;
            }

            command.setExecutor(null);
            command.setTabCompleter(null);
        }

        this.getModifiableData().clear();
    }
}
