package lt.bongibau.smapi;

import lt.bongibau.smapi.commands.CommandManager;
import lt.bongibau.smapi.commands.TestCommand;

public class TestPlugin extends SMPlugin {
    @Override
    public void beforeEnable() {
        CommandManager.getInstance().register(new TestCommand());
    }

    @Override
    public void afterEnable() {
        this.getLogger().info("TestPlugin enabled!");
    }

    @Override
    public void beforeDisable() {
    }

    @Override
    public void afterDisable() {
        this.getLogger().info("TestPlugin disabled!");
    }
}
