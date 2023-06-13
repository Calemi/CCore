package com.calemi.ccore.api.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for Menus for Items.
 */
public abstract class MenuItemBase extends MenuBase {

    private final ItemStack stack;

    /**
     * Creates a Menu.
     * @param menuType  The type of the Menu.
     * @param containerID The container ID of the Menu.
     * @param stack The Item Stack containing this menu.
     */
    public MenuItemBase(@Nullable MenuType<?> menuType, int containerID, ItemStack stack) {
        super(menuType, containerID);
        this.stack = stack;
    }

    public ItemStack getItemStack() {
        return stack;
    }
    
    @Override
    public boolean stillValid(@NotNull Player player) {
        return !stack.isEmpty();
    }
}
