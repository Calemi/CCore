package com.calemi.ccore.api.dispense;

import com.calemi.ccore.api.boat.CBoatType;
import com.calemi.ccore.api.entity.CBoat;
import com.calemi.ccore.api.entity.CChestBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class CBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {

    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
    private final CBoatType type;
    private final boolean hasChest;

    public CBoatDispenseItemBehavior(boolean hasChest, CBoatType type) {
        this.type = type;
        this.hasChest = hasChest;
    }

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {

        ServerLevel level = source.level();
        Vec3 center = source.center();

        Direction direction = source.state().getValue(DispenserBlock.FACING);
        BlockPos blockpos = source.pos().relative(direction);

        double widthOffset = 0.5625 + (double) EntityType.BOAT.getWidth() / 2.0;
        double x = center.x() + (double) direction.getStepX() * widthOffset;
        double y = center.y() + (double)((float) direction.getStepY() * 1.125F);
        double z = center.z() + (double) direction.getStepZ() * widthOffset;

        Boat boat;

        if (hasChest) {
            boat = new CChestBoat(level, x, y, z);
            ((CChestBoat)boat).setBoatType(type);
        }

        else {
            boat = new CBoat(level, x, y, z);
            ((CBoat)boat).setBoatType(type);
        }

        EntityType.<Boat>createDefaultStackConfig(level, stack, null).accept(boat);
        boat.setYRot(direction.toYRot());

        double yOffset;

        if (boat.canBoatInFluid(level.getFluidState(blockpos))) {
            yOffset = 1.0;
        }

        else {

            if (!level.getBlockState(blockpos).isAir() || !boat.canBoatInFluid(level.getFluidState(blockpos.below()))) {
                return defaultDispenseItemBehavior.dispense(source, stack);
            }

            yOffset = 0.0;
        }

        boat.setPos(x, y + yOffset, z);
        level.addFreshEntity(boat);
        stack.shrink(1);

        return stack;
    }

    @Override
    protected void playSound(BlockSource blockSource) {
        blockSource.level().levelEvent(1000, blockSource.pos(), 0);
    }
}