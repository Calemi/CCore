package com.calemi.ccore.api.item;

import com.calemi.ccore.api.general.CCoreMathHelper;
import com.calemi.ccore.api.inventory.InventoryHelper;
import com.calemi.ccore.api.location.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Use this class to help with Items.
 */
public class ItemHelper {

    /**
     * Gives a player an Item Stack. First it adds it to their inventory, then spawns any remaining stacks at the player.
     * @param player The Player to give the item to.
     * @param stack  The Item Stack to spawn.
     */
    public static void giveItem(PlayerEntity player, ItemStack stack, int amount) {

        int fittingSpace = InventoryHelper.calculateFittingSpace(player.getInventory(), stack);

        if (fittingSpace < amount) {
            InventoryHelper.insertItem(player.getInventory(), stack, fittingSpace);

            amount -= fittingSpace;

            spawnStackAtEntity(player, stack, amount);

            return;
        }

        InventoryHelper.insertItem(player.getInventory(), stack, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param location The Location to spawn at.
     * @param stack    The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStackAtLocation(Location location, ItemStack stack, int amount) {
        return spawnStack(location.getWorld(), location.getX() + 0.5F, location.getY() + 0.5F, location.getZ() + 0.5F, stack, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param entity The Entity to spawn at.
     * @param stack  The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStackAtEntity(Entity entity, ItemStack stack, int amount) {
        return spawnStack(entity.getWorld(), (float) entity.getX(), (float) entity.getY() + 0.5F, (float) entity.getZ(), stack, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param world  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(World world, float x, float y, float z, Item item, int amount) {
        return spawnStack(world, x, y, z, new ItemStack(item), amount);
    }

    /**
     * Spawns an Item Stack.
     * @param world  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @param stack  The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(World world, float x, float y, float z, ItemStack stack) {
        return spawnStack(world, x, y, z, stack, stack.getCount());
    }

    /**
     * Spawns an Item Stack.
     * @param world  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @param stack  The Item Stack to spawn.
     * @param amount The amount of items to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(World world, float x, float y, float z, ItemStack stack, int amount) {

        ItemStack stackCopy = stack.copy();
        stackCopy.setCount(amount);

        ItemEntity item = new ItemEntity(world, x, y, z, stackCopy);
        item.setVelocity(-0.05F + CCoreMathHelper.random.nextFloat() * 0.1F, -0.05F + CCoreMathHelper.random.nextFloat() * 0.1F, -0.05F + CCoreMathHelper.random.nextFloat() * 0.1F);
        world.spawnEntity(item);

        return item;
    }
}
