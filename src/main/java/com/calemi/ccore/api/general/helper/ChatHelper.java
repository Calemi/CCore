package com.calemi.ccore.api.general.helper;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;

import java.util.Objects;

/**
 * Use this class to help with chat.
 */
public class ChatHelper {

    /**
     * Used to send a message to everyone in the Level.
     * @param level The level.
     * @param message The message.
     */
    public static void broadcastMessage (Level level, Component message) {
        Objects.requireNonNull(level.getServer()).getPlayerList().broadcastSystemMessage(message, false);
    }
}
