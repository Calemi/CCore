package com.calemi.ccore.api.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Use this class for to help with Containers.
 */
public class InventoryHelper {

    /**
     * @param inventory  The Inventory to count in.
     * @param item      The type of Item to count.
     * @return The amount of Items in an Inventory.
     */
    public static int countItems(Inventory inventory, Item item) {
        return countItems(inventory, new ItemStack(item), false);
    }

    /**
     * @param inventory  The Inventory to count in.
     * @param stack      The type of ItemStack to count.
     * @return The amount of a specified Item Stack in an Inventory. Will compare exact Item Stacks.
     */
    public static int countItems(Inventory inventory, ItemStack stack) {
        return countItems(inventory, stack, true);
    }

    /**
     * @param inventory  The Inventory to count in.
     * @param stack      The type of ItemStack to count.
     * @param compareNBT Should it compare the same NBT items when counting?
     * @return The amount of a specified Item Stack in an Inventory.
     */
    private static int countItems(Inventory inventory, ItemStack stack, boolean compareNBT) {

        boolean isPlayerInventory = inventory instanceof PlayerInventory;

        int count = 0;

        for (int slotIndex = 0; slotIndex < inventory.size(); slotIndex++) {

            //IGNORE ARMOR SLOTS
            if (isPlayerInventory && (slotIndex == 36 || slotIndex == 37 || slotIndex == 38 || slotIndex == 39 || slotIndex == PlayerInventory.OFF_HAND_SLOT)) {
                continue;
            }

            ItemStack stackInSlot = inventory.getStack(slotIndex);

            if (!ItemStack.areItemsEqual(stackInSlot, stack)) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.canCombine(stackInSlot, stack)) {
                    continue;
                }
            }

            count += stackInSlot.getCount();
        }

        return count;
    }

    /**
     * @param inventory  The Inventory to count in.
     * @param item       The Item to test.
     * @return The count of Items that can fit in the Inventory.
     */
    public static int calculateFittingSpace(Inventory inventory, Item item) {
        return calculateFittingSpace(inventory, new ItemStack(item), false);
    }

    /**
     * @param inventory  The Inventory to count in.
     * @param stack      The ItemStack to test.
     * @return The count of Items that can fit in the Inventory. Will compare exact Item Stacks.
     */
    public static int calculateFittingSpace(Inventory inventory, ItemStack stack) {
        return calculateFittingSpace(inventory, stack, true);
    }

    /**
     * @param inventory  The Inventory to count in.
     * @param stack      The ItemStack to test.
     * @param compareNBT Should it only use the same NBT items?
     * @return The count of Items that can fit in the Inventory.
     */
    private static int calculateFittingSpace(Inventory inventory, ItemStack stack, boolean compareNBT) {

        boolean isPlayerInventory = inventory instanceof PlayerInventory;

        int fittingSpace = 0;

        for (int slotIndex = 0; slotIndex < inventory.size(); slotIndex++) {

            //IGNORE ARMOR SLOTS
            if (isPlayerInventory && (slotIndex == 36 || slotIndex == 37 || slotIndex == 38 || slotIndex == 39 || slotIndex == PlayerInventory.OFF_HAND_SLOT)) {
                continue;
            }

            ItemStack stackInSlot = inventory.getStack(slotIndex);

            if (stackInSlot.isEmpty()) {
                fittingSpace += stack.getMaxCount();
            }

            if (!ItemStack.areItemsEqual(stackInSlot, stack)) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.canCombine(stackInSlot, stack)) {
                    continue;
                }
            }

            fittingSpace += stack.getMaxCount() - stackInSlot.getCount();
        }

        return fittingSpace;
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param inventory  The Inventory to remove from.
     * @param item       The type of Item to remove.
     * @param amount     The amount to remove.
     */
    public static void consumeItems(Inventory inventory, Item item, int amount) {
        consumeItems(inventory, new ItemStack(item), amount, false);
    }

    /**
     * Removes an Item Stack from an Inventory.
     * @param inventory  The Inventory to remove from.
     * @param stack      The ItemStack to remove.
     */
    public static void consumeItems(Inventory inventory, ItemStack stack) {
        consumeItems(inventory, stack, stack.getCount(), true);
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param inventory  The Inventory to remove from.
     * @param stack      The ItemStack to remove.
     * @param amount     The amount to remove.
     */
    public static void consumeItems(Inventory inventory, ItemStack stack, int amount) {
        consumeItems(inventory, stack, amount, true);
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param inventory  The Inventory to remove from.
     * @param stack      The type of Item Stack to remove.
     * @param amount     The amount to remove.
     * @param compareNBT Should it compare the same NBT items when consuming?
     */
    private static void consumeItems(Inventory inventory, ItemStack stack, int amount, boolean compareNBT) {

        boolean isPlayerInventory = inventory instanceof PlayerInventory;

        int amountLeft = amount;

        if (countItems(inventory, stack, compareNBT) < amount) {
            return;
        }

        for (int slotIndex = 0; slotIndex < inventory.size(); slotIndex++) {

            //IGNORE ARMOR SLOTS
            if (isPlayerInventory && (slotIndex == 36 || slotIndex == 37 || slotIndex == 38 || slotIndex == 39 || slotIndex == PlayerInventory.OFF_HAND_SLOT)) {
                continue;
            }

            if (amountLeft <= 0) break;

            ItemStack stackInSlot = inventory.getStack(slotIndex);

            if (!ItemStack.areItemsEqual(stackInSlot, stack)) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.canCombine(stackInSlot, stack)) {
                    continue;
                }
            }

            if (amountLeft >= stackInSlot.getCount()) {
                amountLeft -= stackInSlot.getCount();
                inventory.setStack(slotIndex, ItemStack.EMPTY);
            }

            else {
                stackInSlot.decrement(amountLeft);
                return;
            }
        }
    }

    /**
     * @param inventory The Inventory to test.
     * @param item     The Item to test.
     * @param amount    The amount to check for.
     * @return true, if the given Item can be inserted in the Inventory.
     */
    public static boolean canInsertItem(Inventory inventory, Item item, int amount) {
        return calculateFittingSpace(inventory, item) >= amount;
    }

    /**
     * @param inventory The Inventory to test.
     * @param stack     The ItemStack to test.
     * @return true, if the given Item Stack can be inserted in the Inventory.
     */
    public static boolean canInsertItem(Inventory inventory, ItemStack stack) {
        return canInsertItem(inventory, stack, stack.getCount());
    }

    /**
     * @param inventory The Inventory to test.
     * @param stack     The ItemStack to test.
     * @param amount    The amount to check for.
     * @return true, if the given Item Stack can be inserted in the Inventory.
     */
    public static boolean canInsertItem(Inventory inventory, ItemStack stack, int amount) {
        return calculateFittingSpace(inventory, stack) >= amount;
    }

    /**
     * Inserts the given ItemStack into the Inventory.
     * @param inventory The Inventory to insert in.
     * @param item     The Item to insert.
     * @param amount    The amount to insert.
     */
    public static void insertItem(Inventory inventory, Item item, int amount) {
        insertItem(inventory, new ItemStack(item), amount);
    }

    /**
     * Inserts the given ItemStack into the Inventory.
     * @param inventory The Inventory to insert in.
     * @param stack     The ItemStack to insert.
     */
    public static void insertItem(Inventory inventory, ItemStack stack) {
        insertItem(inventory, stack, stack.getCount());
    }

    /**
     * Inserts the given ItemStack into the Inventory.
     * @param inventory The Inventory to insert in.
     * @param stack     The ItemStack to insert.
     * @param amount    The amount to insert.
     */
    public static void insertItem(Inventory inventory, ItemStack stack, int amount) {

        boolean isPlayerInventory = inventory instanceof PlayerInventory;

        if (!canInsertItem(inventory, stack, amount)) {
            return;
        }

        int amountLeft = amount;
        int maxStackSize = stack.getMaxCount();

        for (int slotIndex = 0; slotIndex < inventory.size(); slotIndex++) {

            //IGNORE ARMOR SLOTS
            if (isPlayerInventory && (slotIndex == 36 || slotIndex == 37 || slotIndex == 38 || slotIndex == 39 || slotIndex == PlayerInventory.OFF_HAND_SLOT)) {
                continue;
            }

            if (amountLeft <= 0) break;

            ItemStack stackInSlot = inventory.getStack(slotIndex);

            if (stackInSlot.isEmpty()) {

                int cappedAmount = Math.min(maxStackSize, amountLeft);

                amountLeft -= cappedAmount;

                ItemStack stackCopy = stack.copy();
                stackCopy.setCount(cappedAmount);
                inventory.setStack(slotIndex, stackCopy);

                continue;
            }

            if (!ItemStack.canCombine(stackInSlot, stack)) {
                continue;
            }

            //64 - 2 = 62
            int spaceToMax = maxStackSize - stackInSlot.getCount();

            if (spaceToMax >= amountLeft) {

                stackInSlot.increment(amountLeft);
                break;
            }

            amountLeft -= spaceToMax;
            stackInSlot.setCount(maxStackSize);
        }
    }
}
