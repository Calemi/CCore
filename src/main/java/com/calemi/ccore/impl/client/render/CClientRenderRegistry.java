package com.calemi.ccore.impl.client.render;

import com.calemi.ccore.api.boat.CBoatType;
import com.calemi.ccore.api.boat.CBoatTypeRegistry;
import com.calemi.ccore.api.client.render.CBoatRenderer;
import com.calemi.ccore.impl.entity.CEntities;
import com.calemi.ccore.main.CCoreRef;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CCoreRef.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CClientRenderRegistry {

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(CEntities.BOAT.get(), context -> new CBoatRenderer(context, false));
        event.registerEntityRenderer(CEntities.CHEST_BOAT.get(), context -> new CBoatRenderer(context, true));
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        LayerDefinition boatLayerDefinition = BoatModel.createBodyModel();
        LayerDefinition chestBoatLayerDefinition = ChestBoatModel.createBodyModel();

        for (CBoatType type : CBoatTypeRegistry.getTypes()) {

            event.registerLayerDefinition(CBoatRenderer.createBoatModelName(type), () -> boatLayerDefinition);
            event.registerLayerDefinition(CBoatRenderer.createChestBoatModelName(type), () -> chestBoatLayerDefinition);
        }
    }
}