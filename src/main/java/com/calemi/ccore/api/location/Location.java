package com.calemi.ccore.api.location;

import com.calemi.ccore.api.sound.SoundHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.List;

/**
 * Generic object used to store a location in a Level.
 * Contains helpful Block methods.
 */
public class Location {

    private World world;
    private BlockPos blockPos;

    /**
     * @param world The Level of the Location.
     * @param x     The x position of the Location.
     * @param y     The y position of the Location.
     * @param z     The z position of the Location.
     */
    public Location(World world, int x, int y, int z) {
        this.world = world;
        this.blockPos = new BlockPos(x, y, z);
    }

    /**
     * @param world    The Level of the Location.
     * @param blockPos The Block Position of the Location.
     */
    public Location(World world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }

    /**
     * Creates a Location from a Block Entity.
     * @param blockEntity The Block Entity to get the Location from.
     */
    public Location(BlockEntity blockEntity) {
        this(blockEntity.getWorld(), blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ());
    }

    /**
     * Creates a Location from an Entity.
     * @param entity The Entity to get the Location from.
     */
    public Location(Entity entity) {
        this(entity.getWorld(), entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
    }

    /**
     * @return A new Location with the same values.
     */
    public Location copy() {
        return new Location(this.world, this.blockPos);
    }

    /**
     * @return The Level this Location is in.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Sets the Level this Location is in.
     * @param world The new Level.
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * @return The Block Position of this Location.
     */
    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    /**
     * Sets the Block Position this Location is in.
     * @param blockPos The new Block Position.
     */
    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    /**
     * @return The x position of this Location.
     */
    public int getX() {
        return blockPos.getX();
    }

    /**
     * @return The y position of this Location.
     */
    public int getY() {
        return blockPos.getY();
    }

    /**
     * @return The z position of this Location.
     */
    public int getZ() {
        return blockPos.getZ();
    }

    /**
     * @return A centered Vector at this Location.
     */
    public Vec3d getVector() {
        return new Vec3d(getX() + 0.5D, getY() + 0.5D, getZ() + 0.5D);
    }

    /**
     * Offsets this Location by coordinates.
     * @param x The x amount to move.
     * @param y The y amount to move.
     * @param z The z amount to move.
     */
    public void offset(int x, int y, int z) {
        setBlockPos(blockPos.add(x, y, z));
    }


    /**
     * Offsets this Location in a direction.
     * @param dir      The direction to move.
     * @param distance The distance to move.
     */
    public void relative(Direction dir, int distance) {
        setBlockPos(blockPos.offset(dir, distance));
    }

    /**
     * Offsets this Location in a direction.
     * @param dir The direction to move.
     */
    public void relative(Direction dir) {
        setBlockPos(blockPos.offset(dir));
    }

    /**
     * @return The Block at this Location.
     */
    public Block getBlock() {
        return getBlockState().getBlock();
    }

    /**
     * @return The current light value at this Location.
     */
    public int getLightValue() {
        return world.getLightLevel(getBlockPos());
    }

    /**
     * @param player    The Player who is breaking the Block.
     * @param heldStack The Item Stack being held by the Player.
     * @return The Block's drops at this Location.
     */
    public List<ItemStack> getBlockDropsFromBreaking(PlayerEntity player, ItemStack heldStack) {
        return Block.getDroppedStacks(getBlockState(), (ServerWorld) world, getBlockPos(), getBlockEntity(), player, heldStack);
    }

    /**
     * @return The BlockState at this Location.
     */
    public BlockState getBlockState() {
        return world.getBlockState(blockPos);
    }

    /**
     * @return The BlockEntity at this Location.
     */
    public BlockEntity getBlockEntity() {
        return world.getWorldChunk(getBlockPos()).getBlockEntity(getBlockPos(), WorldChunk.CreationType.IMMEDIATE);
    }

    /**
     * @param location Reference Location.
     * @return The distance (in Blocks) between this Location and another.
     */
    public double getDistance(Location location) {

        int dx = getX() - location.getX();
        int dy = getY() - location.getY();
        int dz = getZ() - location.getZ();

        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    /**
     * Sets the Block at this location.
     * @param state The new BlockState.
     */
    public void setBlockWithUpdate(BlockState state) {
        world.setBlockState(getBlockPos(), state, 3);
    }

    /**
     * Sets the Block at this location.
     * @param state  The new BlockState.
     * @param placer The Player who created this change.
     */
    public void setBlockWithUpdate(BlockState state, PlayerEntity placer) {
        setBlockWithUpdate(state);
        state.getBlock().onPlaced(world, getBlockPos(), state, placer, new ItemStack(state.getBlock()));
    }

    /**
     * Sets the Block at this location.
     * @param block The new Block.
     */
    public void setBlockWithUpdate(Block block) {
        setBlockWithUpdate(block.getDefaultState());
    }

    /**
     * Sets the Block at this location.
     * @param block   The new Block.
     * @param context The context.
     */
    public void setBlockWithUpdate(Block block, ItemPlacementContext context) {
        setBlockWithUpdate(block.getPlacementState(context));
    }

    /**
     * Sets the Block at this location to Air.
     */
    public void setBlockToAir() {
        setBlockWithUpdate(Blocks.AIR);
    }

    /**
     * Breaks the Block at this location.
     * @param breaker The Player who broke the block.
     */
    public void breakBlock(PlayerEntity breaker) {

        SoundHelper.playBlockBreak(this, getBlockState());

        if (breaker instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) breaker).interactionManager.tryBreakBlock(blockPos);
        }
    }

    /**
     * @return true if this Location is at 0, 0, 0.
     */
    public boolean isZero() {
        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

    /**
     * @return true if the Block at this Location is Air.
     */
    public boolean isAirBlock() {
        return getBlock() == Blocks.AIR;
    }

    /**
     * @return true if a Block could be placed at this Location.
     */
    public boolean isBlockValidForPlacing() {
        return getBlockState().isReplaceable() || isAirBlock();
    }

    /**
     * @return true if the Block at this Location is a full cube.
     */
    public boolean isFullCube() {
        return getBlockState().isFullCube(world, getBlockPos());
    }

    /**
     * @param entity The Entity to check for.
     * @return true if the Entity exists at this Location
     */
    public boolean isEntityAtLocation(Entity entity) {

        int entityX = entity.getBlockX();
        int entityY = entity.getBlockY();
        int entityZ = entity.getBlockZ();

        return entityX == getX() && entityZ == getZ() && (entityY == getY() || entityY + 1 == getY());
    }

    /**
     * @return true if the Block at this Location has collision.
     */
    public boolean doesBlockHaveCollision() {
        return getBlockState().getCollisionShape(world, getBlockPos(), ShapeContext.absent()) != VoxelShapes.empty();
    }

    /**
     * @param world The Level to construct the Location.
     * @param nbt   The tag that stored the Location.
     * @return A location constructed from an NBT Tag.
     */
    public static Location readFromNBT(World world, NbtCompound nbt) {

        //Checks if the tag is missing a crucial value. If so, doesn't read the Location.
        if (!nbt.contains("locX") || !nbt.contains("locY") || !nbt.contains("locZ")) {
            return null;
        }

        int x = nbt.getInt("locX");
        int y = nbt.getInt("locY");
        int z = nbt.getInt("locZ");

        return new Location(world, x, y, z);
    }

    /**
     * Stores the Location in an NBT Tag.
     * @param nbt The tag to store the Location.
     */
    public void writeToNBT(NbtCompound nbt) {
        nbt.putInt("locX", getX());
        nbt.putInt("locY", getY());
        nbt.putInt("locZ", getZ());
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Location newLoc) {
            return world.equals(newLoc.world) && getBlockPos().equals(newLoc.getBlockPos());
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + ", " + getZ() + "]";
    }
}
