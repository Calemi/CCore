package com.calemi.ccore.api.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public abstract class BaseScreen extends Screen {

    protected final Minecraft mc;
    protected final Player player;

    public BaseScreen(Component component) {
        super(component);
        mc = Minecraft.getInstance();
        player = mc.player;
    }

    protected abstract int getGuiSizeX();

    protected abstract int getGuiSizeY();

    protected abstract boolean canCloseWithInvKey();

    protected int getScreenX() {
        return (width - getGuiSizeX()) / 2;
    }

    protected int getScreenY() {
        return (height - getGuiSizeY()) / 2;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (minecraft != null && canCloseWithInvKey() && keyCode == minecraft.options.keyInventory.getKey().getValue()) {
            onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
