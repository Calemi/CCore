package com.calemi.ccore.api.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A custom slot that will only allow a certain Item to be placed in.
 */
public class SlotFilter extends Slot {

    private final List<Item> itemFilters;

    /**
     * Creates a Filter Slot.
     * @param container The container to add the Slot in.
     * @param index The id of the Slot.
     * @param x The x coordinate of the Slot.
     * @param y The y coordinate of the Slot.
     * @param filters The Items that can be placed in the Slot.
     */
    public SlotFilter(Container container, int index, int x, int y, Item... filters) {
        super(container, index, x, y);
        this.itemFilters = new ArrayList<>(Arrays.asList(filters));
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {

        if (itemFilters != null) {
            for (Item itemFilter : itemFilters) {
                if (itemFilter == stack.getItem()) return true;
            }
        }

        return false;
    }
}
