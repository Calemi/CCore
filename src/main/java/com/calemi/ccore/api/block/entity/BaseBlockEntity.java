package com.calemi.ccore.api.block.entity;

import com.calemi.ccore.api.location.BlockLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Use this class for all Block Entities.
 */
public abstract class BaseBlockEntity extends BlockEntity {

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    public BlockLocation getLocation() {
        Level level = getLevel();
        if (level == null) return null;
        return new BlockLocation(getLevel(), getBlockPos());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        Level level = getLevel();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider holder) {
        return saveCustomOnly(holder);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holder) {
        super.handleUpdateTag(tag, holder);
        setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider holder) {
        handleUpdateTag(pkt.getTag(), holder);
    }
}
