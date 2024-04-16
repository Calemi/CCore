package com.calemi.ccore.api.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class FakeSlotWidget extends PressableWidget {

    private ItemStack stack;
    protected final PressAction onPress;

    public FakeSlotWidget(int x, int y, ItemStack stack, PressAction onPress) {
        super(x, y, 16, 16, Text.empty());
        this.stack = stack;
        this.onPress = onPress;
    }

    public void render(int mouseX, int mouseY, DrawSystem drawSystem, DrawContext context) {
        drawSystem.drawItemWithTooltip(stack, getX() - drawSystem.getScreenX(), getY() - drawSystem.getScreenY(), mouseX, mouseY, context);
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {}

    public ItemStack getItemStack() {
        return this.stack;
    }

    public void setItemStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void onPress() {

        ItemStack cursorStack = MinecraftClient.getInstance().player.currentScreenHandler.getCursorStack().copy();
        cursorStack.setCount(1);

        stack = cursorStack;
        onPress.onPress(this);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(FakeSlotWidget button);
    }
}
