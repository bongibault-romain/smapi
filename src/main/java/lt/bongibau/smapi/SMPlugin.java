package lt.bongibau.smapi;

import lt.bongibau.smapi.adapter.AdapterManager;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryUnLoadingException;
import lt.bongibau.smapi.commands.CommandManager;
import lt.bongibau.smapi.managers.SMManagerRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public abstract class SMPlugin extends JavaPlugin {

    public abstract void beforeEnable();

    public abstract void afterEnable();

    public abstract void beforeDisable();

    public abstract void afterDisable();

    @Override
    public void onEnable() {
        this.beforeEnable();

        SMManagerRegistry.getInstance().register(Arrays.asList(
                CommandManager.getInstance(),
                AdapterManager.getInstance()
        ));

        try {
            SMManagerRegistry.getInstance().load(this);
        } catch (SMRegistryLoadingException e) {
            this.getLogger().severe(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
        }

        this.afterEnable();
    }

    @Override
    public void onDisable() {
        this.beforeDisable();

        try {
            SMManagerRegistry.getInstance().unload(this);
        } catch (SMRegistryUnLoadingException e) {
            this.getLogger().severe(e.getMessage());
            return;
        }

        this.afterDisable();
    }
}
