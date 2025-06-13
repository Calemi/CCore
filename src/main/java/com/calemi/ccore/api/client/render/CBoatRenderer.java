package com.calemi.ccore.api.client.render;

import com.calemi.ccore.api.boat.CBoatType;
import com.calemi.ccore.api.boat.CBoatTypeRegistry;
import com.calemi.ccore.api.entity.CBoat;
import com.calemi.ccore.api.entity.CChestBoat;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.joml.Quaternionf;

import java.util.Map;

public class CBoatRenderer extends EntityRenderer<Boat> {

    private final Map<CBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public CBoatRenderer(EntityRendererProvider.Context context, boolean hasChest) {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = CBoatTypeRegistry.getTypes().stream().collect(ImmutableMap.toImmutableMap((key) -> key, (model) ->
                Pair.of(ResourceLocation.fromNamespaceAndPath(model.getModId(), getTextureLocation(model, hasChest)), createBoatModel(context, model, hasChest))));
    }

    @Override
    public void render(Boat boat, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));

        float f = (float) boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(partialTicks);

        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = this.getModelWithLocation(boat);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> listmodel = pair.getSecond();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);

        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RenderType.waterMask());

            if (listmodel instanceof WaterPatchModel waterpatchmodel) {
                waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
        super.render(boat, yaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Boat boat) {
        return this.getModelWithLocation(boat).getFirst();
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if (boat instanceof CChestBoat)
            return this.boatResources.get(((CChestBoat) boat).getBoatType());
        else
            return this.boatResources.get(((CBoat) boat).getBoatType());
    }

    private static String getTextureLocation(CBoatType boatType, boolean hasChest) {
        return hasChest ? "textures/entity/chest_boat/" + boatType.getName() + ".png" : "textures/entity/boat/" + boatType.getName() + ".png";
    }

    private static ModelLayerLocation createLocation(String modId, String name, String layer) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(modId, name), layer);
    }

    public static ModelLayerLocation createBoatModelName(CBoatType boatType) {
        return createLocation(boatType.getModId(), "boat/" + boatType.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(CBoatType boatType) {
        return createLocation(boatType.getModId(), "chest_boat/" + boatType.getName(), "main");
    }

    private BoatModel createBoatModel(EntityRendererProvider.Context context, CBoatType boatType, boolean hasChest) {
        ModelLayerLocation modellayerlocation = hasChest ? createChestBoatModelName(boatType) : createBoatModelName(boatType);
        ModelPart baked = context.bakeLayer(modellayerlocation);
        return hasChest ? new ChestBoatModel(baked) : new BoatModel(baked);
    }
}