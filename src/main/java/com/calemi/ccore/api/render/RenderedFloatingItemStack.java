package com.calemi.ccore.api.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class RenderedFloatingItemStack extends RenderedItemStack {

    private final float spinSpeed;
    private final float hoverSpeed;
    private final float hoverHeight;
    private long lastTime;
    private float spin;
    private float hover;

    public RenderedFloatingItemStack(float spinSpeed, float hoverSpeed, float hoverHeight) {
        this.spinSpeed = spinSpeed;
        this.hoverSpeed = hoverSpeed;
        this.hoverHeight = hoverHeight;
    }

    public RenderedFloatingItemStack() {
        this(1.0F, 1.0F, 1.0F);
    }

    public void updateSpinningAndFloating() {

        long targetTime = 10L;

        if (System.currentTimeMillis() - this.lastTime >= targetTime) {
            this.lastTime = System.currentTimeMillis();
            this.spin += this.spinSpeed;
            this.spin %= 360.0F;
            this.hover += 0.025F * this.hoverSpeed;
            this.hover = (float)((double)this.hover % 6.283185307179586);
        }
    }

    public void applyTranslations(MatrixStack matrices) {

        if (!this.getStack().isEmpty()) {
            matrices.translate(0.5, 0.5 + Math.sin(this.hover) / 5.0F * this.hoverHeight, 0.5);
        }
    }

    public void applyRotations(MatrixStack matrices) {

        if (!this.getStack().isEmpty()) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.spin));
        }
    }
}
