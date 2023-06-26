package lt.bongibau.smapi.adapter;

import lt.bongibau.smapi.adapter.string.*;
import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.registries.SMInstanceManager;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

import java.util.Arrays;

public class AdapterManager extends SMInstanceManager<SMAdapter<?, ?>> {
    private static final AdapterManager instance = new AdapterManager();

    public static AdapterManager getInstance() {
        return instance;
    }

    @Override
    protected void onLoad() throws SMRegistryLoadingException {
        this.register(Arrays.asList(
                new PlayerAdapter(),
                new IntegerAdapter(),
                new DoubleAdapter(),
                new BooleanAdapter(),
                new FloatAdapter()
        ));
    }

    @Override
    protected void onUnload() throws SMRegistryUnLoadingException {
        this.getData().forEach(this::unregister);
    }
}
