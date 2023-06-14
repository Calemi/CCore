package com.calemi.ccore.api.general.helper;

import com.calemi.ccore.api.general.ItemSpawnProfile;
import com.calemi.ccore.api.general.Location;
import com.calemi.ccore.main.CCoreRef;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Use this class to help with Items.
 */
public class ItemHelper {

    /**
     * Gives a player an Item Stack. First it adds it to their inventory, then spawns any remaining stacks at the player.
     * @param player The Player to give the item to.
     * @param stack  The Item Stack to spawn.
     */
    public static void giveItem(Player player, ItemStack stack, int amount) {

        int fittingSpace = ContainerHelper.calculateFittingSpace(player.getInventory(), stack);
        ContainerHelper.insertStack(player.getInventory(), stack, fittingSpace);

        amount -= fittingSpace;

        ItemSpawnProfile spawnProfile = new ItemSpawnProfile().setStack(stack).setAmount(amount).setTarget(player);
        spawnProfile.spawn();
    }

    /**
     * Spawns an Item Stack.
     * @param location The Location to spawn at.
     * @param stack    The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStackAtLocation(Location location, ItemStack stack, int amount) {
        return spawnStack(location.getLevel(), location.getX() + 0.5F, location.getY() + 0.5F, location.getZ() + 0.5F, stack, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param entity The Entity to spawn at.
     * @param stack  The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStackAtEntity(Entity entity, ItemStack stack, int amount) {
        return spawnStack(entity.level(), (float) entity.getX(), (float) entity.getY() + 0.5F, (float) entity.getZ(), stack, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param level  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(Level level, float x, float y, float z, Item item, int amount) {
        return spawnStack(level, x, y, z, item, amount);
    }

    /**
     * Spawns an Item Stack.
     * @param level  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @param stack  The Item Stack to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(Level level, float x, float y, float z, ItemStack stack) {
        return spawnStack(level, x, y, z, stack, stack.getCount());
    }

    /**
     * Spawns an Item Stack.
     * @param level  The Level to spawn in.
     * @param x      The x position to spawn at.
     * @param y      The y position to spawn at.
     * @param z      The z position to spawn at.
     * @param stack  The Item Stack to spawn.
     * @param amount The amount of items to spawn.
     * @return The Item Entity that spawned.
     */
    public static ItemEntity spawnStack(Level level, float x, float y, float z, ItemStack stack, int amount) {

        ItemStack stackCopy = stack.copy();
        stackCopy.setCount(amount);

        ItemEntity item = new ItemEntity(level, x, y, z, stackCopy);
        item.setNoPickUpDelay();
        item.setDeltaMovement(-0.05F + MathHelper.random.nextFloat() * 0.1F, -0.05F + MathHelper.random.nextFloat() * 0.1F, -0.05F + MathHelper.random.nextFloat() * 0.1F);
        level.addFreshEntity(item);
        return item;
    }
}
