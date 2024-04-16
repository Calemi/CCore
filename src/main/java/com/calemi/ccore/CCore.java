package com.calemi.ccore;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCore implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("CCore");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Main...");
	}
}