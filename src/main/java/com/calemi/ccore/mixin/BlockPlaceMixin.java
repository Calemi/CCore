package com.calemi.ccore.mixin;

import com.calemi.ccore.api.event.BlockPlaceCallback;
import com.calemi.ccore.api.event.BlockPlacePostCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (BlockItem.class)
public class BlockPlaceMixin {

    @Inject(method = "postPlacement", at = @At("HEAD"), cancellable = true)
    private void postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> callback) {

        if (BlockPlacePostCallback.EVENT.invoker().placeBlockPost(pos, world, player, stack, state).isAccepted()) {
            callback.setReturnValue(false);
        }
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    private void postPlacement(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> callback) {

        if (BlockPlaceCallback.EVENT.invoker().placeBlock(context).isAccepted()) {
            callback.setReturnValue(ActionResult.FAIL);
        }
    }
}