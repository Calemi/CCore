package com.calemi.ccore.api.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class RenderedItemStack {

    private ItemStack stack;

    public RenderedItemStack() {
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, BlockPos lightPos, int light, int overlay) {

        if (!this.stack.isEmpty()) {

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, world, 1);
        }
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
