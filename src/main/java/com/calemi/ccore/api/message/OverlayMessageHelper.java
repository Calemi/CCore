package com.calemi.ccore.api.message;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class OverlayMessageHelper {

    public static void displaySuccessMsg(MutableComponent text, ServerPlayer player) {
        displayMsg(text.withStyle(ChatFormatting.GREEN), player);
    }

    public static void displayWarningMsg(MutableComponent text, ServerPlayer player) {
        displayMsg(text.withStyle(ChatFormatting.YELLOW), player);
    }

    public static void displayErrorMsg(MutableComponent text, ServerPlayer player) {
        displayMsg(text.withStyle(ChatFormatting.RED), player);
    }

    public static void displayMsg(MutableComponent text, ServerPlayer player) {

        ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(text);
        player.connection.send(packet);
    }
}