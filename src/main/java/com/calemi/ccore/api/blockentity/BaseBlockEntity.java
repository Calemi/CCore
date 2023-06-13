package com.calemi.ccore.api.blockentity;

import com.calemi.ccore.api.general.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for Block Entities.
 */
public abstract class BaseBlockEntity extends BlockEntity {

    /**
     * @param type The type of the Block Entity.
     * @param pos The position of the Block Entity.
     * @param state The Block State of the Block Entity.
     */
    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @return The Location of the Block Entity.
     */
    public Location getLocation() {
        return new Location(getLevel(), getBlockPos());
    }

    /**
     * Call this method to sync server NBT data to the all clients.
     */
    public void markUpdated() {

        if (getLevel() != null ) {
            setChanged();
            getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    /*
        Packet Methods
     */

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
