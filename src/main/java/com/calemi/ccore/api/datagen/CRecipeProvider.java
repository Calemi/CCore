package com.calemi.ccore.api.datagen;

import com.calemi.ccore.api.block.family.CBlockFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class CRecipeProvider extends RecipeProvider {

    protected final String modId;

    public CRecipeProvider(String modId, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.modId = modId;
    }

    protected void family(CBlockFamily family, RecipeOutput recipeOutput) {

        Block baseBlock = family.getBlock(CBlockFamily.MemberType.BASE);
        Block log = family.getBlock(CBlockFamily.MemberType.LOG);
        Block wood = family.getBlock(CBlockFamily.MemberType.WOOD);
        Block strippedLog = family.getBlock(CBlockFamily.MemberType.STRIPPED_LOG);
        Block strippedWood = family.getBlock(CBlockFamily.MemberType.STRIPPED_WOOD);
        Block crackedBlock = family.getBlock(CBlockFamily.MemberType.CRACKED);
        Block chiseled = family.getBlock(CBlockFamily.MemberType.CHISELED);
        Block pillar = family.getBlock(CBlockFamily.MemberType.PILLAR);
        Block stairs = family.getBlock(CBlockFamily.MemberType.STAIRS);
        Block slab = family.getBlock(CBlockFamily.MemberType.SLAB);
        Block wall = family.getBlock(CBlockFamily.MemberType.WALL);
        Block fence = family.getBlock(CBlockFamily.MemberType.FENCE);
        Block fenceGate = family.getBlock(CBlockFamily.MemberType.FENCE_GATE);
        Block door = family.getBlock(CBlockFamily.MemberType.DOOR);
        Block trapDoor = family.getBlock(CBlockFamily.MemberType.TRAPDOOR);
        Block pressurePlate = family.getBlock(CBlockFamily.MemberType.PRESSURE_PLATE);
        Block button = family.getBlock(CBlockFamily.MemberType.BUTTON);
        Block sign = family.getBlock(CBlockFamily.MemberType.SIGN);
        Block hangingSign = family.getBlock(CBlockFamily.MemberType.HANGING_SIGN);

        for (int ancestorIndex = 0; ancestorIndex < family.getAncestors().size(); ancestorIndex++) {

            Block ancestorBaseBlock =  family.getAncestors().get(ancestorIndex).getBlock(CBlockFamily.MemberType.BASE);

            if (ancestorBaseBlock == null) continue;

            if (baseBlock != null) {
                if (ancestorIndex == 0) twoByTwo(baseBlock, 4, ancestorBaseBlock, null, recipeOutput);
                stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, baseBlock, ancestorBaseBlock);
            }

            if (stairs != null) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, stairs, ancestorBaseBlock);
            if (slab != null) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, slab, ancestorBaseBlock, 2);
            if (wall != null) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, wall, ancestorBaseBlock);
            if (chiseled != null) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, chiseled, ancestorBaseBlock);
            if (pillar != null) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, pillar, ancestorBaseBlock);
        }

        if (log != null && baseBlock != null && family.getLogTag() != null) planks(family, recipeOutput);
        if (log != null && wood != null) wood(family, recipeOutput);
        if (strippedLog != null && strippedWood != null) strippedWood(family, recipeOutput);
        if (crackedBlock != null && baseBlock != null) smeltingResultFromBase(recipeOutput, crackedBlock, baseBlock);
        if (chiseled != null) chiseled(family, recipeOutput);
        if (pillar != null) pillar(family, recipeOutput);
        if (stairs != null) stairs(family, recipeOutput);
        if (slab != null) slab(family, recipeOutput);
        if (wall != null) wall(family, recipeOutput);
        if (fence != null) fence(family, recipeOutput);
        if (fenceGate != null) fenceGate(family, recipeOutput);
        if (door != null) door(family, recipeOutput);
        if (trapDoor != null) trapDoor(family, recipeOutput);
        if (pressurePlate != null) pressurePlate(family, recipeOutput);
        if (button != null) button(family, recipeOutput);
        if (sign != null) sign(family, recipeOutput);
        if (hangingSign != null) hangingSign(family, recipeOutput);
    }

    protected void stonecutter(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material) {
        stonecutter(recipeOutput, category, result, material, 1);
    }

    protected void stonecutter(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getConversionRecipeName(result, material) + "_stonecutting"));
    }

    protected void oneToOne(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, @Nullable String group, int resultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount)
                .requires(ingredient)
                .group(group)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getConversionRecipeName(result, ingredient)));
    }

    protected void twoByTwo(ItemLike result, int count, ItemLike ingredient, @Nullable String group, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .define('#', ingredient)
                .pattern("##")
                .pattern("##")
                .group(group)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result) + "_from_" + getItemName(ingredient)));
    }

    protected void oneByTwoVertical(RecipeOutput recipeOutput, ItemLike result, int count, ItemLike ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, result, count)
                .define('X', ingredient)
                .pattern("X")
                .pattern("X")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void sword(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("X")
                .pattern("X")
                .pattern("S")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void shovel(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern(" X ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void pickaxe(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("XXX")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void axe(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("XX ")
                .pattern("XS ")
                .pattern(" S ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void hoe(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("XX ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void helmet(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void chestplate(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void leggings(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void boots(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('X', material)
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void wood(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.WOOD);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.LOG);

        twoByTwo(result, 3, ingredient, "bark", recipeOutput);
    }

    protected void strippedWood(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.STRIPPED_WOOD);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.STRIPPED_LOG);

        twoByTwo(result, 3, ingredient, "bark", recipeOutput);
    }

    protected void planks(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.BASE);
        TagKey<Item> ingredient = family.getLogTag();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .requires(ingredient)
                .group("planks")
                .unlockedBy("has_logs", has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void chiseled(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.CHISELED);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);
        Block slab = family.getBlock(CBlockFamily.MemberType.SLAB);

        stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient);
        oneByTwoVertical(recipeOutput, result, 1, slab);
    }

    protected void pillar(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.PILLAR);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient);
        oneByTwoVertical(recipeOutput, result, 1, ingredient);
    }

    protected void stairs(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.STAIRS);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        stairBuilder(result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_stairs" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));

        if (family.getFamilyType().isStone()) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient);
    }

    protected void slab(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.SLAB);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_slab" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));

        if (family.getFamilyType().isStone()) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient, 2);
    }

    protected void wall(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.WALL);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        wallBuilder(RecipeCategory.BUILDING_BLOCKS, result, Ingredient.of(ingredient))
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));

        if (family.getFamilyType().isStone()) stonecutter(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient);
    }

    protected void fence(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.FENCE);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 3)
                .define('W', ingredient)
                .define('#', Tags.Items.RODS_WOODEN)
                .pattern("W#W")
                .pattern("W#W")
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_fence" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void fenceGate(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.FENCE_GATE);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', ingredient)
                .pattern("#W#")
                .pattern("#W#")
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_fence_gate" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void door(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.DOOR);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        doorBuilder(result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_door" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void trapDoor(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.TRAPDOOR);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        trapdoorBuilder(result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_trapdoor" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void pressurePlate(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.PRESSURE_PLATE);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        pressurePlateBuilder(RecipeCategory.REDSTONE, result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_pressure_plate" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void button(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.BUTTON);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        buttonBuilder(result, Ingredient.of(ingredient))
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_button" : null)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void sign(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.SIGN);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 3)
                .group(family.getFamilyType().equals(CBlockFamily.FamilyType.PLANKS) ? "wooden_sign" : null)
                .define('#', ingredient)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected void hangingSign(CBlockFamily family, RecipeOutput recipeOutput) {

        Block result = family.getBlock(CBlockFamily.MemberType.HANGING_SIGN);
        Block ingredient = family.getBlock(CBlockFamily.MemberType.BASE);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 6)
                .group("hanging_sign")
                .define('#', ingredient)
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(ingredient))
                .save(recipeOutput, rl(getItemName(result)));
    }

    protected ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }
}
