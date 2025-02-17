package com.calemi.ccore;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("ccore")
public class CCore {

    public static final Logger LOGGER = LoggerFactory.getLogger("CCore");

    public CCore(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Initializing Main...");
    }
}
