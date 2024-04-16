package com.calemi.ccore.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;

public interface BlockPlaceCallback {

    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class,
            (listeners) -> (context) -> {
                for (BlockPlaceCallback listener : listeners) {
                    ActionResult result = listener.placeBlock(context);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult placeBlock(ItemPlacementContext context);
}
