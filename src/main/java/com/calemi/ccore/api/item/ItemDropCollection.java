package com.calemi.ccore.api.item;

import com.calemi.ccore.api.location.Location;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class ItemDropCollection {

    private final List<ItemStack> drops;

    public ItemDropCollection() {
        drops = new ArrayList<>();
    }

    public void addDrop(ItemStack stack) {

        int currentCount = stack.getCount();

        //Check for same item and merge stacks.
        for (ItemStack drop : drops) {

            if (ItemStack.isSameItemSameComponents(drop, stack) && drop.getCount() <= drop.getMaxStackSize()) {

                int spaceLeft = drop.getMaxStackSize() - drop.getCount();

                if (currentCount > spaceLeft) {
                    currentCount -= spaceLeft;
                    drop.setCount(drop.getMaxStackSize());
                }

                else {
                    drop.setCount(drop.getCount() + stack.getCount());
                    return;
                }
            }
        }

        stack.setCount(currentCount);

        //New items get appended.
        drops.add(stack);
    }

    public void dropAll(Location location) {

        for (ItemStack drop : drops) {

            ItemSpawnProfile itemSpawnProfile = new ItemSpawnProfile().setStack(drop).setDestination(location);
            itemSpawnProfile.spawn();
        }
    }
}
