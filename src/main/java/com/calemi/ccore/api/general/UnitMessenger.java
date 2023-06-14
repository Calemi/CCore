package com.calemi.ccore.api.general;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Used to send message from a "Unit".
 * Messages sent will have the Unit's name in brackets ex. [Furnace]
 */
public class UnitMessenger {

    /**
     * The Unit's name key.
     */
    private final String unitNameKey;

    /**
     * Creates a Unit Messenger.
     * @param unitNameKey The Unit's name key.
     */
    public UnitMessenger(String unitNameKey) {
        this.unitNameKey = unitNameKey;
    }

    /**
     * Sends a message to specified Players.
     * @param msgComponent The message to send.
     * @param players The Players to send to.
     */
    public void sendMessage(MutableComponent msgComponent, Player... players) {

        for (Entity player : players) {

            MutableComponent component = getUnitName().append(msgComponent.withStyle(ChatFormatting.GREEN));
            player.sendSystemMessage(component);
        }
    }

    /**
     * Sends an error message to specified Players.
     * @param msgComponent The message to send.
     * @param players The Players to send to.
     */
    public void sendErrorMessage(MutableComponent msgComponent, Player... players) {

        for (Entity player : players) {

            MutableComponent component = getUnitName().append(msgComponent.withStyle(ChatFormatting.RED));
            player.sendSystemMessage(component);
        }
    }

    /**
     * @param messageKey The message key.
     * @param args The arguments if needed.
     * @return The message component with the prefix already included.
     */
    public MutableComponent getMessage(String messageKey, Object... args) {
        return Component.translatable("unit." + unitNameKey + ".msg." + messageKey, args);
    }

    /**
     * @return The Unit's name in brackets.
     */
    private MutableComponent getUnitName() {
        return Component.literal("[").append(Component.translatable("unit.", unitNameKey, ".name").append(Component.literal("]")));
    }
}
