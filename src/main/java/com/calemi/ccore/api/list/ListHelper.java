package com.calemi.ccore.api.list;

import com.calemi.ccore.api.block.family.CBlockFamily;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ListHelper {

    public static List<ResourceKey<Block>> toBlockResourceKeyList(List<Block> blocks) {
        return new ArrayList<>(blocks.stream().map(block -> BuiltInRegistries.BLOCK.getResourceKey(block).orElse(null)).toList());
    }

    public static List<ResourceKey<Item>> toItemResourceKeyList(List<Item> blocks) {
        return new ArrayList<>(blocks.stream().map(item -> BuiltInRegistries.ITEM.getResourceKey(item).orElse(null)).toList());
    }

    public static List<Block> toBlockListFromBlockSet(List<CBlockFamily> blockSet) {
        List<Block> list = new ArrayList<>();
        blockSet.forEach(set -> list.addAll(set.getAllBlocks()));
        return list;
    }

    public static Block[] toBlockArray(List <Block> blocks) {
        return blocks.toArray(Block[]::new);
    }

    public static Item[] toItemArray(List<Block> blocks) {
        return blocks.stream().map(Block::asItem).toArray(Item[]::new);
    }

    public static List<Item> toItemListFromBlock(List<Block> blocks) {
        return new ArrayList<>(blocks.stream().map(Block::asItem).filter(item -> item instanceof BlockItem).toList());
    }

    public static List<ItemStack> toItemStackListFromBlock(List<Block> blocks) {
        return new ArrayList<>(blocks.stream().map(block -> new ItemStack(block.asItem())).toList());
    }

    public static List<ItemStack> toItemStackListFromItem(List<Item> items) {
        return new ArrayList<>(items.stream().map((ItemStack::new)).toList());
    }

    public static List<ItemStack> toItemStackListFromItemLike(List<ItemLike> items) {
        return new ArrayList<>(items.stream().map(ItemStack::new).toList());
    }
}
