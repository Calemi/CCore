package com.calemi.ccore.api.message;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class OverlayMessageHelper {

    public static void displaySuccessMsg(MutableText text, PlayerEntity player) {
        displayMsg(text.formatted(Formatting.GREEN), player);
    }

    public static void displayWarningMsg(MutableText text, PlayerEntity player) {
        displayMsg(text.formatted(Formatting.YELLOW), player);
    }

    public static void displayErrorMsg(MutableText text, PlayerEntity player) {
        displayMsg(text.formatted(Formatting.RED), player);
    }

    public static void displayMsg(MutableText text, PlayerEntity player) {

        if (!player.getWorld().isClient()) {
            OverlayMessageS2CPacket messagePacket = new OverlayMessageS2CPacket(text);
            ((ServerPlayerEntity)player).networkHandler.sendPacket(messagePacket);
        }
    }

    public static void displaySuccessMsgClient(MutableText text, PlayerEntity player) {
        displayMsgClient(text.formatted(Formatting.GREEN), player);
    }

    public static void displayWarningMsgClient(MutableText text, PlayerEntity player) {
        displayMsgClient(text.formatted(Formatting.YELLOW), player);
    }

    public static void displayErrorMsgClient(MutableText text, PlayerEntity player) {
        displayMsgClient(text.formatted(Formatting.RED), player);
    }

    public static void displayMsgClient(MutableText text, PlayerEntity player) {

        if (player.getWorld().isClient()) {
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(text, false);
        }
    }
}
