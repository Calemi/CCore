package com.calemi.ccore.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import org.joml.Quaternionf;

/**
 * Used to render an Item Stack in the Level.
 */
public class RenderedFloatingItemStack extends RenderedItemStack {

    private final float spinSpeed;
    private final float hoverSpeed;
    private final float hoverHeight;

    private long lastTime;
    private float spin;
    private float hover;

    /**
     * @param spinSpeed The spinning speed.
     * @param hoverSpeed The speed of hovering.
     * @param hoverHeight The apex of the hover height.
     */
    public RenderedFloatingItemStack(float spinSpeed, float hoverSpeed, float hoverHeight) {
        this.spinSpeed = spinSpeed;
        this.hoverSpeed = hoverSpeed;
        this.hoverHeight = hoverHeight;
    }

    public RenderedFloatingItemStack() {
        this(1, 1, 1);
    }

    /**
     * Call this method every tick to keep item rotating and floating.
     */
    public void updateSpinningAndFloating() {

        long targetTime = 10;

        if (System.currentTimeMillis() - lastTime >= targetTime) {
            lastTime = System.currentTimeMillis();
            spin += spinSpeed;
            spin %= 360;
            hover += (0.025F * hoverSpeed);
            hover %= 2 * Math.PI;
        }
    }

    /**
     * Call this method to apply the translations.
     */
    public void applyTranslations(PoseStack poseStack) {

        if (!getStack().isEmpty()) {

            float blockYOffset = 0;

            if (getStack().getItem() instanceof BlockItem) {
                blockYOffset = -0.125F;
            }

            poseStack.translate(0.5D, 0.5D + blockYOffset + ((Mth.sin(hover) / 5) * hoverHeight), 0.5D);
        }
    }

    /**
     * Call this method to apply the rotations.
     */
    public void applyRotations(PoseStack poseStack) {

        if (!getStack().isEmpty()) {
            poseStack.rotateAround(new Quaternionf(0, 1, 0, 0), 0, spin, 0);
        }
    }

    /**
     * Call this method to the scale.
     */
    public void applyScale(PoseStack poseStack) {

        if (!getStack().isEmpty()) {

            float blockScale = 1;

            if (getStack().getItem() instanceof BlockItem) {
                blockScale = 1.5F;
            }

            poseStack.scale(blockScale, blockScale, blockScale);
        }
    }
}
