package com.calemi.ccore.impl.loot.modifier;

import com.calemi.ccore.api.loot.modifier.BonusItemLootModifier;
import com.calemi.ccore.main.CCore;
import com.calemi.ccore.main.CCoreRef;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CCoreRef.ID);

    public static final Supplier<MapCodec<BonusItemLootModifier>> BONUS_ITEM =
            LOOT_MODIFIERS.register("bonus_item", () -> BonusItemLootModifier.CODEC);

    public static void init() {
        CCore.LOGGER.info("Registering: Loot Modifiers - Start");
        LOOT_MODIFIERS.register(CCore.MOD_EVENT_BUS);
        CCore.LOGGER.info("Registering: Loot Modifiers - End");
    }
}