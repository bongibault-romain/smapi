package lt.bongibau.smapi.adapter;

import lt.bongibau.smapi.SMPlugin;
import lt.bongibau.smapi.adapter.string.*;
import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.api.managers.SMInstanceManager;
import lt.bongibau.smapi.api.managers.SMManagerInfo;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.api.managers.exceptions.SMRegistryUnLoadingException;

import java.util.Arrays;

@SMManagerInfo(dependencies = {})
public class AdapterManager extends SMInstanceManager<SMAdapter<?, ?>> {
    private static final AdapterManager instance = new AdapterManager();

    public static AdapterManager getInstance() {
        return instance;
    }

    @Override
    protected void onLoad(SMPlugin plugin) throws SMRegistryLoadingException {
        this.register(Arrays.asList(
                new PlayerAdapter(),
                new IntegerAdapter(),
                new DoubleAdapter(),
                new BooleanAdapter(),
                new FloatAdapter(),
                new StringAdapter(),
                new OfflinePlayerAdapter()
        ));
    }

    @Override
    protected void onUnload(SMPlugin plugin) throws SMRegistryUnLoadingException {
        this.getModifiableData().clear();
    }
}
