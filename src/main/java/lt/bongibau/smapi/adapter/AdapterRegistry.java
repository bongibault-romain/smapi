package lt.bongibau.smapi.adapter;

import lt.bongibau.smapi.adapter.string.*;
import lt.bongibau.smapi.api.adapter.SMAdapter;
import lt.bongibau.smapi.registries.InstanceSMRegistry;
import lt.bongibau.smapi.registries.exceptions.SMRegistryLoadingException;
import lt.bongibau.smapi.registries.exceptions.SMRegistryUnLoadingException;

import java.util.Arrays;

public class AdapterRegistry extends InstanceSMRegistry<SMAdapter<?, ?>> {
    private static final AdapterRegistry instance = new AdapterRegistry();

    public static AdapterRegistry getInstance() {
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
