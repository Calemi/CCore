package com.calemi.ccore.api.general.helper;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Use this class for to help with Containers.
 */
public class ContainerHelper {

    /**
     * @param container  The Container to count in.
     * @param item      The type of Item to count.
     * @return The amount of Items in an Inventory.
     */
    public static int countItems(Container container, Item item) {
        return countItems(container, new ItemStack(item), false);
    }

    /**
     * @param container  The Container to count in.
     * @param stack      The type of Item Stack to count.
     * @return The amount of a specified Item Stack in an Inventory. Will compare exact Item Stacks.
     */
    public static int countItems(Container container, ItemStack stack) {
        return countItems(container, stack, true);
    }

    /**
     * @param container  The Container to count in.
     * @param stack      The type of Item Stack to count.
     * @param compareNBT Should it compare the same NBT items when counting?
     * @return The amount of a specified Item Stack in an Inventory.
     */
    private static int countItems(Container container, ItemStack stack, boolean compareNBT) {

        int count = 0;

        for (int slotIndex = 0; slotIndex < container.getContainerSize(); slotIndex++) {

            ItemStack stackInSlot = container.getItem(slotIndex);

            if (!stackInSlot.is(stack.getItem())) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                    continue;
                }
            }

            count += stackInSlot.getCount();
        }

        return count;
    }

    /**
     * @param container  The Container to count in.
     * @param item       The Item to test.
     * @return The count of Items that can fit in the Container.
     */
    public static int calculateFittingSpace(Container container, Item item) {
        return calculateFittingSpace(container, new ItemStack(item), false);
    }

    /**
     * @param container  The Container to count in.
     * @param stack      The Item Stack to test.
     * @return The count of Items that can fit in the Container. Will compare exact Item Stacks.
     */
    public static int calculateFittingSpace(Container container, ItemStack stack) {
        return calculateFittingSpace(container, stack, true);
    }

    /**
     * @param container  The Container to count in.
     * @param stack      The Item Stack to test.
     * @param compareNBT Should it only use the same NBT items?
     * @return The count of Items that can fit in the Container.
     */
    private static int calculateFittingSpace(Container container, ItemStack stack, boolean compareNBT) {

        int fittingSpace = 0;

        for (int slotIndex = 0; slotIndex < container.getContainerSize(); slotIndex++) {

            ItemStack stackInSlot = container.getItem(slotIndex);

            if (stackInSlot.isEmpty()) {
                fittingSpace += stack.getMaxStackSize();
            }

            if (!stackInSlot.is(stack.getItem())) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                    continue;
                }
            }

            fittingSpace += stack.getMaxStackSize() - stackInSlot.getCount();
        }

        return fittingSpace;
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param container  The Container to remove from.
     * @param item       The type of Item to remove.
     * @param amount     The amount to remove.
     */
    public static void consumeItems(Container container, Item item, int amount) {
        consumeItems(container, new ItemStack(item), amount, false);
    }

    /**
     * Removes an Item Stack from an Inventory.
     * @param container  The Container to remove from.
     * @param stack      The Item Stack to remove.
     */
    public static void consumeItems(Container container, ItemStack stack) {
        consumeItems(container, stack, stack.getCount(), true);
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param container  The Container to remove from.
     * @param stack      The Item Stack to remove.
     * @param amount     The amount to remove.
     */
    public static void consumeItems(Container container, ItemStack stack, int amount) {
        consumeItems(container, stack, amount, true);
    }

    /**
     * Removes a specified amount of Items from an Inventory.
     * @param container  The Container to remove from.
     * @param stack      The type of Item Stack to remove.
     * @param amount     The amount to remove.
     * @param compareNBT Should it compare the same NBT items when consuming?
     */
    private static void consumeItems(Container container, ItemStack stack, int amount, boolean compareNBT) {

        int amountLeft = amount;

        if (countItems(container, stack, compareNBT) < amount) {
            return;
        }

        for (int slotIndex = 0; slotIndex < container.getContainerSize(); slotIndex++) {

            if (amountLeft <= 0) break;

            ItemStack stackInSlot = container.getItem(slotIndex);

            if (!stackInSlot.is(stack.getItem())) {
                continue;
            }

            if (compareNBT) {

                if (!ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                    continue;
                }
            }

            if (amountLeft >= stackInSlot.getCount()) {
                amountLeft -= stackInSlot.getCount();
                container.setItem(slotIndex, ItemStack.EMPTY);
            }

            else {
                amountLeft -= stackInSlot.getCount();
                stackInSlot.shrink(amountLeft);
            }
        }
    }

    public static boolean canInsertStack(Container container, ItemStack stack) {
        return canInsertStack(container, stack, stack.getCount());
    }

    /**
     * @param container The Container to test.
     * @param stack     The Item Stack to test.
     * @param amount    The amount to check for.
     * @return true, if the given Item Stack can be inserted in the Container.
     */
    public static boolean canInsertStack(Container container, ItemStack stack, int amount) {

        int amountLeft = amount;

        for (int slotIndex = 0; slotIndex < container.getContainerSize(); slotIndex++) {

            ItemStack stackInSlot = container.getItem(slotIndex);

            if (!container.canPlaceItem(slotIndex, stack)) {
                continue;
            }

            if (stackInSlot.isEmpty()) {
                amountLeft -= stack.getMaxStackSize();
                continue;
            }

            if (!ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                continue;
            }

            int spaceLeftInStack = stack.getMaxStackSize() - stackInSlot.getCount();
            amountLeft -= spaceLeftInStack;
        }

        return amountLeft <= 0;
    }

    public static void insertStack(Container container, ItemStack stack) {
        insertStack(container, stack, stack.getCount());
    }

    /**
     * Inserts the given ItemStack into the Container.
     * @param container The Container to insert in.
     * @param stack     The ItemStack to insert.
     * @param amount    The amount to insert.
     */
    public static void insertStack(Container container, ItemStack stack, int amount) {

        if (!canInsertStack(container, stack, amount)) {
            return;
        }

        int amountLeft = amount;
        int maxStackSize = stack.getMaxStackSize();

        for (int slotIndex = 0; slotIndex < container.getContainerSize(); slotIndex++) {

            if (amountLeft <= 0) break;

            ItemStack stackInSlot = container.getItem(slotIndex);

            if (stackInSlot.isEmpty()) {

                amountLeft -= maxStackSize;

                ItemStack stackCopy = stack.copy();
                stackCopy.setCount(maxStackSize);
                container.setItem(slotIndex, stackCopy);

                continue;
            }

            if (ItemStack.isSameItemSameTags(stackInSlot, stack)) {
                continue;
            }

            int spaceToMax = maxStackSize -= stackInSlot.getCount();

            if (spaceToMax >= amountLeft) {

                int left = spaceToMax - amountLeft;
                amountLeft = 0;

                stackInSlot.setCount(stackInSlot.getCount() + left);

                break;
            }

            amountLeft -= spaceToMax;
            stackInSlot.setCount(maxStackSize);
        }
    }
}
