package com.calemi.ccore.api.log;

import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.Logger;

/**
 * Use this class to help log information.
 * Great for debugging!
 */
public class LogHelper {

    /**
     * Use this method to send a message to the console on both the client and server side.
     * This method will also show what side the message came from.
     * NOTE: Will only show up when running in debug mode.
     * @param logger The logger to use.
     * @param level   Used to determine the side.
     * @param message The message you wish to print.
     */
    public static void logCommon(Logger logger, Level level, Object message) {
        logger.info("{}{}", level.isClientSide() ? "[CLIENT] " : "[SERVER] ", message);
    }
}