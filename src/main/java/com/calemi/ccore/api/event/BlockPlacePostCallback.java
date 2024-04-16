package com.calemi.ccore.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface BlockPlacePostCallback {

    Event<BlockPlacePostCallback> EVENT = EventFactory.createArrayBacked(BlockPlacePostCallback.class,
            (listeners) -> (pos, world, player, stack, state) -> {
                for (BlockPlacePostCallback listener : listeners) {
                    ActionResult result = listener.placeBlockPost(pos, world, player, stack, state);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult placeBlockPost(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state);
}
