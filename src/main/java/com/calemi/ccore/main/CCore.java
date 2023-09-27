package com.calemi.ccore.main;

import com.calemi.ccore.test.registry.ItemRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CCoreRef.MOD_ID)
public class CCore {

    public static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public CCore() {

        ItemRegistry.init();
    }
}
