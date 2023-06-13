package com.calemi.ccore.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Used to render an Item Stack in the Level.
 */
public class RenderedItemStack {

    private ItemStack stack;

    public RenderedItemStack() {
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Renders the floating item in the world. Will not render an empty stack.
     */
    public void render(@Nullable Level level, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        if (!stack.isEmpty()) {
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, buffer, level, 0);
        }
    }
}
