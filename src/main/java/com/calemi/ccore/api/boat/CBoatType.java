package com.calemi.ccore.api.boat;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class CBoatType {

    private final String modId;
    private final String name;
    private final DeferredItem<Item> boatItem;
    private final DeferredItem<Item> chestBoatItem;
    private final DeferredBlock<Block> planks;

    public CBoatType(String modId, String name, DeferredItem<Item> boatItem, DeferredItem<Item> chestBoatItem, DeferredBlock<Block> planks) {
        this.modId = modId;
        this.name = name;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
        this.planks = planks;
        CBoatTypeRegistry.register(this);
    }

    public String getModId() {
        return modId;
    }

    public String getName() {
        return name;
    }

    public DeferredItem<Item> getBoatItem() {
        return boatItem;
    }

    public DeferredItem<Item> getChestBoatItem() {
        return chestBoatItem;
    }

    public DeferredBlock<Block> getPlanks() {
        return planks;
    }
}
