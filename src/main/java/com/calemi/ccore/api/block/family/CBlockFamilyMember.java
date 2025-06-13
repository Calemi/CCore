package com.calemi.ccore.api.block.family;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * This class represent a single family member.
 * Used to determine if it should generate data files or show up in creative tabs.
 */
public class CBlockFamilyMember {

    private final Block block;
    private boolean showInCreativeTabs = true;
    private boolean genBlockState = true;
    private boolean genLootTable = true;
    private boolean genBlockTags = true;

    CBlockFamilyMember(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public boolean genBlockState() {
        return genBlockState;
    }

    public boolean genLootTable() {
        return genLootTable;
    }

    public boolean genBlockTags() {
        return genBlockTags;
    }

    public boolean showInCreativeTabs() {
        return showInCreativeTabs;
    }

    public static class Builder {

        private final CBlockFamilyMember member;

        public Builder(Block block) {
            member = new CBlockFamilyMember(block);
        }

        public Builder(DeferredBlock<Block> block) {
            this(block.get());
        }

        public Builder dontGenOrShow() {
            return dontShowInCreativeTabs().dontGenBlockState().dontGenLootTable().dontGenBlockTags();
        }

        public Builder dontShowInCreativeTabs() {
            member.showInCreativeTabs = false;
            return this;
        }

        public Builder dontGenBlockState() {
            member.genBlockState = false;
            return this;
        }

        public Builder dontGenLootTable() {
            member.genLootTable = false;
            return this;
        }

        public Builder dontGenBlockTags() {
            member.genBlockTags = false;
            return this;
        }

        public CBlockFamilyMember getMember() {
            return member;
        }
    }
}
