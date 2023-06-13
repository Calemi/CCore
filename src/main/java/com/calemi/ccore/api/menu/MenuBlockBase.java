package com.calemi.ccore.api.menu;

import com.calemi.ccore.api.blockentity.BaseContainerBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for Menus for Blocks.
 */
public abstract class MenuBlockBase extends MenuBase {

    private final BaseContainerBlockEntity blockEntity;

    /**
     * Creates a Menu.
     * @param menuType The type of the Menu.
     * @param containerID The container ID of the Menu.
     * @param blockEntity The Block Entity containing this menu.
     */
    protected MenuBlockBase(@Nullable MenuType<?> menuType, int containerID, BaseContainerBlockEntity blockEntity) {
        super(menuType, containerID);
        this.blockEntity = blockEntity;
    }

    public BaseContainerBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return getBlockEntity().stillValid(player);
    }
}
