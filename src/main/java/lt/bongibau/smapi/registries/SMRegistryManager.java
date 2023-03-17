package lt.bongibau.smapi.registries;

public class SMRegistryManager extends SMRegistry {

    private static final SMRegistryManager instance = new SMRegistryManager();

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public static SMRegistryManager getInstance() {
        return instance;
    }
}
