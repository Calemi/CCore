package com.calemi.ccore.api.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for Menus.
 */
public abstract class MenuBase extends AbstractContainerMenu {

    /**
     * Creates a Menu.
     * @param menuType The type of the Menu.
     * @param containerID The container ID of the Menu.
     */
    protected MenuBase(@Nullable MenuType<?> menuType, int containerID) {
        super(menuType, containerID);
    }

    /**
     * @return The amount of slots the Menu contains.
     */
    public abstract int getContainerSize();

    /**
     * Helper method that adds the Player's Slots to the menu.
     * @param playerInv The Player's Inventory.
     * @param yOffset The y offset coordinate for the Player's Slots.
     */
    public void addPlayerInventory(Inventory playerInv, int yOffset) {

        //Inventory
        for(int rowY = 0; rowY < 3; rowY++) {
            for(int rowX = 0; rowX < 9; rowX++) {
                addSlot(new Slot(playerInv, rowX + rowY * 9 + 9, 8 + rowX * 18, yOffset + rowY * 18));
            }
        }

        //Hotbar
        for(int rowX = 0; rowX < 9; rowX++) {
            addSlot(new Slot(playerInv, rowX, 8 + rowX * 18, yOffset + 58));
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {

        int containerSize = getContainerSize();

        ItemStack clickedStackCopy = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {

            ItemStack clickedStack = slot.getItem();
            clickedStackCopy = clickedStack.copy();

            //CONTAINER ---> PLAYER
            if (index < containerSize) {

                if (!moveItemStackTo(clickedStack, containerSize, containerSize + 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //PLAYER ---> CONTAINER
            else if (!moveItemStackTo(clickedStack, 0, containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            if (clickedStack.getCount() == clickedStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, clickedStack);
        }

        return clickedStackCopy;
    }
}
