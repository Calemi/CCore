package com.calemi.ccore.test.item;

import com.calemi.ccore.api.general.helper.ContainerHelper;
import com.calemi.ccore.api.general.helper.ItemHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TestItem extends Item {

    public TestItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack heldStack = player.getItemInHand(hand);
        Container inventory = player.getInventory();

        if (level.isClientSide()) {
            return InteractionResultHolder.success(heldStack);
        }

        //countItemsTest(player, inventory);
        //calculateFittingSpaceTest(player, inventory);
        //consumeItemsTest(player, inventory);
        //canInsertItemTest(player, inventory);
        //insertItemTest(player, inventory);

        ItemHelper.giveItem(player, new ItemStack(Items.APPLE),  64);

        return InteractionResultHolder.success(heldStack);
    }

    //Tests

    private void countItemsTest(Player player, Container container) {

        int appleCount = ContainerHelper.countItems(container, Items.APPLE);
        player.sendSystemMessage(Component.literal("countItems: " + appleCount + " Apples."));

        int pickaxeCount = ContainerHelper.countItems(container, new ItemStack(Items.IRON_PICKAXE));
        player.sendSystemMessage(Component.literal("countItems: " + pickaxeCount + " Iron Pickaxes (no damage)."));
    }

    private void calculateFittingSpaceTest(Player player, Container container) {

        int appleSpace = ContainerHelper.calculateFittingSpace(container, Items.APPLE);
        player.sendSystemMessage(Component.literal("calculateFittingSpace: " + appleSpace + " space for Apples."));

        int pickaxeSpace = ContainerHelper.calculateFittingSpace(container, new ItemStack(Items.IRON_PICKAXE));
        player.sendSystemMessage(Component.literal("calculateFittingSpace: " + pickaxeSpace + " space for Iron Pickaxes (no damage)."));
    }

    private void consumeItemsTest(Player player, Container container) {

        ContainerHelper.consumeItems(container, Items.APPLE, 8);
        ContainerHelper.consumeItems(container, new ItemStack(Items.IRON_PICKAXE), 2);
    }

    private void canInsertItemTest(Player player, Container container) {

        boolean canInsertApples = ContainerHelper.canInsertItem(container, Items.APPLE, 128);
        player.sendSystemMessage(Component.literal("canInsertItem: " + canInsertApples + " for 128 Apples."));

        boolean canInsertPickaxes = ContainerHelper.canInsertItem(container, new ItemStack(Items.IRON_PICKAXE), 2);
        player.sendSystemMessage(Component.literal("canInsertItem: " + canInsertPickaxes + " for 2 Iron Pickaxes (no damage)."));
    }

    private void insertItemTest(Player player, Container container) {

        ContainerHelper.insertItem(container, Items.APPLE, 128);
        ContainerHelper.insertItem(container, new ItemStack(Items.IRON_PICKAXE), 2);
    }
}
