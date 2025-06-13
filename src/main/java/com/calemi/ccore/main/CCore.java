package com.calemi.ccore.main;

import com.calemi.ccore.impl.entity.CEntities;
import com.calemi.ccore.impl.loot.condition.CLootItemConditions;
import com.calemi.ccore.impl.loot.modifier.CLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("ccore")
public class CCore {

    public static IEventBus MOD_EVENT_BUS;

    public static final Logger LOGGER = LoggerFactory.getLogger(CCoreRef.NAME);

    public CCore(IEventBus modEventBus, ModContainer modContainer) {

        LOGGER.info("Registering: Main - Start");

        MOD_EVENT_BUS = modEventBus;

        CEntities.init();

        CLootModifiers.init();
        CLootItemConditions.init();

        LOGGER.info("Registering: Main - End");
    }
}
