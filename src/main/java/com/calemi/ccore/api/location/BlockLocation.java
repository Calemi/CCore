package com.calemi.ccore.api.location;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Generic object used to represent a block position in a Level.
 * Contains helpful Block methods.
 */
public class BlockLocation {

    private Level level;
    private BlockPos blockPos;

    /*
        CONSTRUCTORS
     */

    /**
     * @param level The Level of the BlockLocation.
     * @param x     The x position of the BlockLocation.
     * @param y     The y position of the BlockLocation.
     * @param z     The z position of the BlockLocation.
     */
    public BlockLocation(Level level, int x, int y, int z) {
        this.level = level;
        this.blockPos = new BlockPos(x, y, z);
    }

    /**
     * @param level    The Level of the BlockLocation.
     * @param blockPos The BlockPos of the BlockLocation.
     */
    public BlockLocation(Level level, BlockPos blockPos) {
        this(level, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    /**
     * Creates a BlockLocation from a BlockEntity.
     * @param blockEntity The BlockEntity to get the BlockLocation from.
     */
    public BlockLocation(BlockEntity blockEntity) {
        this(blockEntity.getLevel(), blockEntity.getBlockPos());
    }

    /**
     * Creates a Location from an Entity.
     * @param entity The Entity to get the BlockLocation from.
     */
    public BlockLocation(Entity entity) {
        this(entity.level(), entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
    }

    /**
     * @return A new BlockLocation with the same Level and BlockPos.
     */
    public BlockLocation copy() {
        return new BlockLocation(level, blockPos);
    }

    /**
     * Offsets this Location by coordinates.
     * @param x The x amount to move.
     * @param y The y amount to move.
     * @param z The z amount to move.
     * @return This BlockLocation. Does not copy.
     */
    public BlockLocation offset(int x, int y, int z) {
        setBlockPos(blockPos.offset(x, y, z));
        return this;
    }

    /**
     * Offsets this Location in a Direction by a specified distance.
     * @param dir      The Direction to move.
     * @param distance The distance to move.
     * @return This BlockLocation. Does not copy.
     */
    public BlockLocation relative(Direction dir, int distance) {
        setBlockPos(blockPos.relative(dir, distance));
        return this;
    }

    /**
     * Offsets this Location in a Direction by 1.
     * @param dir The Direction to move.
     * @return This BlockLocation. Does not copy.
     */
    public BlockLocation relative(Direction dir) {
        return relative(dir, 1);
    }

    /*
        GETTER & SETTERS
     */

    /**
     * @return The Level this BlockLocation is in.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the Level this BlockLocation is in.
     * @param level The new Level.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return The BlockPos of this BlockLocation.
     */
    public BlockPos getBlockPos() {
        return blockPos;
    }

    /**
     * Sets the BlockPos this BlockLocation is in.
     * @param blockPos The new BlockPos.
     */
    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    /**
     * @return The x position of this BlockLocation.
     */
    public int getX() {
        return blockPos.getX();
    }

    /**
     * @return The y position of this BlockLocation.
     */
    public int getY() {
        return blockPos.getY();
    }

    /**
     * @return The z position of this BlockLocation.
     */
    public int getZ() {
        return blockPos.getZ();
    }

    /*
        LEVEL METHODS
     */

    /**
     * @return The Block at this BlockLocation.
     */
    public Block getBlock() {
        return getBlockState().getBlock();
    }

    /**
     * Sets the old Block at the BlockLocation to a new specified BlockState.
     * @param newState The BlockState to place.
     * @param flags Flags for different types of placement events. See Level's setBlock method for more information.
     * @param recursionLeft The amount of chain-reaction neighbor changes to happen.
     * @return True, if placed successfully.
     */
    public boolean setBlock(BlockState newState, int flags, int recursionLeft) {
        return getLevel().setBlock(getBlockPos(), newState, flags, recursionLeft);
    }

    /**
     * Sets the old Block at the BlockLocation to a new specified BlockState.
     * With 512 chain-reaction neighbor changes.
     * @param newState The BlockState to place.
     * @param flags Flags for different types of placement events. See Level's setBlock() method for more information.
     * @return True, if placed successfully.
     */
    public boolean setBlock(BlockState newState, int flags) {
        return setBlock(newState, flags, 512);
    }

    /**
     * Sets the old Block at the BlockLocation to a new specified BlockState.
     * Additionally, updates the state to clients and notifies neighboring Blocks.
     * With 512 chain-reaction neighbor changes.
     * @param newState The BlockState to place.
     * @return True, if placed successfully.
     */
    public boolean setBlock(BlockState newState) {
        return getLevel().setBlockAndUpdate(getBlockPos(), newState);
    }

    /**
     * Sets the Block at the BlockLocation to air. 
     * Keeps FluidState if the Block was waterlogged.
     * @param isMoving Set to true, if the block was moving.
     * @return True, if removed successfully.
     */
    public boolean removeBlock(boolean isMoving) {
        return getLevel().removeBlock(getBlockPos(), isMoving);
    }

    /**
     * Destroys the Block at the BlockLocation as if a Player were to.
     * Plays destroy particles.
     * Keeps FluidState if the Block was waterlogged.
     * @param dropBlock Set to true, for Block to drop its LootTable.
     * @param entity The entity responsible for destroying the Block. Can be null.
     * @param recursionLeft The amount of chain-reaction neighbor changes to happen.
     * @return  True, if destroyed successfully.
     */
    public boolean destroyBlock(boolean dropBlock, @Nullable Entity entity, int recursionLeft) {
        return getLevel().destroyBlock(getBlockPos(), dropBlock, entity, recursionLeft);
    }


    /**
     * Renders the Block breaking overlay at the BlockLocation.
     * Must be called client-side.
     * Can be called on the server but is essentially functionless.
     * @param breakerId The id of the breaker. Usually set to Player.getId();
     * @param progress The amount of progress. 0-10. Set to -1 to prevent overlay from rendering.
     */
    public void destroyBlockProgress(int breakerId, int progress) {
        getLevel().destroyBlockProgress(breakerId, getBlockPos(), progress);
    }

    /**
     * @return The BlockState at this BlockLocation.
     */
    public BlockState getBlockState() {
        return getLevel().getBlockState(getBlockPos());
    }

    /**
     * @param state The BlockState to check.
     * @return True, if the specified BlockState matches the one at this BlockLocation.
     */
    public boolean isState(Predicate<BlockState> state) {
        return getLevel().isStateAtPosition(getBlockPos(), state);
    }

    /**
     * @return The FluidState at this BlockLocation.
     */
    public FluidState getFluidState() {
        return getLevel().getFluidState(getBlockPos());
    }

    /**
     * @param state The FluidState to check.
     * @return True, if the specified FluidState matches the one at this BlockLocation.
     */
    public boolean isFluid(Predicate<FluidState> state) {
        return getLevel().isFluidAtPosition(getBlockPos(), state);
    }    

    /**
     * @return The BlockEntity at this BlockLocation.
     */
    public BlockEntity getBlockEntity() {
        return getLevel().getBlockEntity(getBlockPos());
    }

    /**
     * Removes the BlockEntity at this BlockLocation.
     */
    public void removeBlockEntity() {
        getLevel().removeBlockEntity(getBlockPos());
    }

    /**
     * Call to mark the BlockEntity at this BlockLocation as changed.
     */
    public void blockEntityChanged() {
        getLevel().blockEntityChanged(getBlockPos());
    }

    /**
     * @return True, if this BlockLocation is within the Level's current bounds.
     */
    public boolean isInWorldBounds() {
        return getLevel().isInWorldBounds(getBlockPos());
    }

    /**
     * @return True, if this BlockLocation is currently being chunk-loaded.
     */
    public boolean isLoaded() {
        return getLevel().isLoaded(getBlockPos());
    }

    /**
     * @param player The Player to check perms.
     * @return True, if the specified Player is able to interact at this BlockLocation.
     */
    public boolean mayInteract(Player player) {
        return getLevel().mayInteract(player , getBlockPos());
    }


    /**
     * @return True, if it is raining at this BlockLocation.
     */
    public boolean isRaining() {
        return getLevel().isRainingAt(getBlockPos());
    }

    /**
     * @return The LevelChunk this BlockLocation is apart of.
     */
    public LevelChunk getChunk() {
        return getLevel().getChunkAt(getBlockPos());
    }

    /**
     * Fires a vanilla built-in block event.
     * @param block The Block to fire it on.
     * @param eventId The id if the event.
     * @param eventParam Extra params for certain events.
     */
    public void blockEvent(Block block, int eventId, int eventParam) {
        getLevel().blockEvent(getBlockPos(), block, eventId, eventParam);
    }

    /**
     * Fires a vanilla built-in global event.
     * @param eventId  The id if the event.
     * @param data Extra data for certain events.
     */
    public void globalLevelEntity(int eventId, int data) {
        getLevel().globalLevelEvent(eventId, getBlockPos(), data);
    }

    /**
     * @return The current difficulty at this BlockLocation.
     */
    public DifficultyInstance getCurrentDifficulty() {
        return getLevel().getCurrentDifficultyAt(getBlockPos());
    }

    /*
        SOUND EVENTS
     */

    /**
     * @param state The BlockState to get the SoundType from.
     * @return The SoundType of the specified BlockState.
     */
    public SoundType getSoundType(BlockState state) {
        return state.getSoundType(getLevel(), getBlockPos(), null);
    }

    /**
     * @return The SoundType of the BlockState at this BlockLocation.
     */
    public SoundType getSoundType() {
        return getBlockState().getSoundType(getLevel(), getBlockPos(), null);
    }

    /*
        UTILITY METHODS
     */

    /**
     * @param blockLocation Reference BlockLocation.
     * @return The distance (in Blocks) between this BlockLocation and another.
     */
    public double getDistance(BlockLocation blockLocation) {

        int dx = getX() - blockLocation.getX();
        int dy = getY() - blockLocation.getY();
        int dz = getZ() - blockLocation.getZ();

        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof BlockLocation compareBlockLocation) {
            return level.equals(compareBlockLocation.getLevel()) && getBlockPos().equals(compareBlockLocation.getBlockPos());
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + ", " + getZ() + "]";
    }
}