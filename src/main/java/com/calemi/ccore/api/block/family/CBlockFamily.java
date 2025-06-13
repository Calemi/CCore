package com.calemi.ccore.api.block.family;

import com.google.common.collect.Maps;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Use this class to define a Block Family. A Block Family is a set of blocks that contains all the different permutations and shapes.
 * Example: Cobblestone's Block Family would be Cobblestone, Cobblestone Stairs, Cobblestone Wall, ...
 */
public class CBlockFamily {

    private final FamilyType familyType;
    private final LinkedHashMap<MemberType, CBlockFamilyMember> members;
    private final List<CBlockFamily> ancestors;
    private TagKey<Item> logTag;

    CBlockFamily(FamilyType familyType) {
        this.familyType = familyType;
        this.members = Maps.newLinkedHashMap();
        this.ancestors = new ArrayList<>();
        logTag = null;
    }

    public FamilyType getFamilyType() {
        return familyType;
    }

    public Map<MemberType, CBlockFamilyMember> getMembers() {
        return members;
    }

    public List<CBlockFamily> getAncestors() {
        return ancestors;
    }

    public TagKey<Item> getLogTag() {
        return logTag;
    }

    public CBlockFamilyMember getMember(MemberType type) {
        return members.get(type);
    }

    public Block getBlock(MemberType type) {
        CBlockFamilyMember member = getMember(type);
        if (member != null) return member.getBlock();
        return null;
    }

    public List<Block> getAllBlocks() {
        return getMembers().values().stream().map(CBlockFamilyMember::getBlock).toList();
    }

    public List<Item> getAllTabItems() {
        return getMembers().values().stream().filter(CBlockFamilyMember::showInCreativeTabs).map(member -> member.getBlock().asItem()).toList();
    }

    public enum FamilyType {
        STONE,
        COBBLESTONE,
        PLANKS;

        public boolean isStone() {
            return this == STONE || this == COBBLESTONE;
        }
    }

    public enum MemberType {
        LOG,
        WOOD,
        STRIPPED_LOG,
        STRIPPED_WOOD,
        BASE,
        CRACKED,
        STAIRS,
        SLAB,
        WALL,
        PILLAR,
        CHISELED,
        FENCE,
        FENCE_GATE,
        DOOR,
        TRAPDOOR,
        PRESSURE_PLATE,
        BUTTON,
        SIGN,
        WALL_SIGN,
        HANGING_SIGN,
        WALL_HANGING_SIGN;
    }

    public static class Builder {

        private final CBlockFamily family;

        public Builder(FamilyType familyType) {
            family = new CBlockFamily(familyType);
        }

        public Builder logTag(TagKey<Item> logTag) {
            family.logTag = logTag;
            return this;
        }

        public Builder member(MemberType memberType, CBlockFamilyMember.Builder memberBuilder) {
            family.members.put(memberType, memberBuilder.getMember());
            return this;
        }

        public Builder ancestor(CBlockFamily ancestor) {
            family.ancestors.add(ancestor);
            return this;
        }

        public CBlockFamily getFamily() {
            return family;
        }
    }
}
