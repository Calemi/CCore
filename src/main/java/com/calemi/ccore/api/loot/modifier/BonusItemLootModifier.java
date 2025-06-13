package com.calemi.ccore.api.loot.modifier;

import com.calemi.ccore.api.math.MathHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class BonusItemLootModifier extends LootModifier {

    private final float chance;
    private final ItemStack bonusItem;

    public BonusItemLootModifier(LootItemCondition[] conditionsIn, float chance, ItemStack bonusItem) {
        super(conditionsIn);
        this.chance = chance;
        this.bonusItem = bonusItem;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (MathHelper.rollChance(chance)) generatedLoot.add(bonusItem.copy());
        return generatedLoot;
    }

    public static final MapCodec<BonusItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions),
                            Codec.FLOAT.fieldOf("chance").forGetter(b -> b.chance),
                            ItemStack.CODEC.fieldOf("bonus_item").forGetter(b -> b.bonusItem))
                    .apply(instance, BonusItemLootModifier::new)
    );

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}