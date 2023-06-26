package lt.bongibau.smapi;

import lt.bongibau.smapi.adapter.AdapterManager;
import lt.bongibau.smapi.commands.CommandManager;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SMPlugin extends JavaPlugin {

    private static SMPlugin instance = null;

    public abstract void beforeEnable();

    public abstract void afterEnable();

    public abstract void beforeDisable();

    public abstract void afterDisable();

    @Override
    public void onEnable() {
        instance = this;

        this.beforeEnable();

        try {
            AdapterManager.getInstance().load();
            CommandManager.getInstance().load();
        } catch (SMRegistryLoadingException e) {
            this.getLogger().severe(e.getMessage());
        }

        this.afterEnable();
    }

    @Override
    public void onDisable() {
        try {
            CommandManager.getInstance().unload();
            AdapterManager.getInstance().unload();
        } catch (SMRegistryUnLoadingException e) {
            this.getLogger().severe(e.getMessage());
        }

        instance = null;
    }

    public static SMPlugin getInstance() {
        return instance;
    }
}
