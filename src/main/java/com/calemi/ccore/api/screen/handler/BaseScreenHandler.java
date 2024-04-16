package com.calemi.ccore.api.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for Menus.
 */
public abstract class BaseScreenHandler extends ScreenHandler {

    /**
     * Creates a Menu.
     * @param screenHandlerType The type of the Screen Handler.
     * @param syncId The sync ID of the Screen Handler.
     */
    protected BaseScreenHandler(@Nullable ScreenHandlerType<?> screenHandlerType, int syncId) {
        super(screenHandlerType, syncId);
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
    public ItemStack quickMove(@NotNull PlayerEntity player, int index) {

        int containerSize = getContainerSize();

        ItemStack clickedStackCopy = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasStack()) {

            ItemStack clickedStack = slot.getStack();
            clickedStackCopy = clickedStack.copy();

            //CONTAINER ---> PLAYER
            if (index < containerSize) {

                if (!insertItem(clickedStack, containerSize, containerSize + 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //PLAYER ---> CONTAINER
            else if (!insertItem(clickedStack, 0, containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();

            if (clickedStack.getCount() == clickedStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, clickedStack);
        }

        return clickedStackCopy;
    }
}
