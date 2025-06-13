package com.calemi.ccore.impl.loot.condition;

import com.calemi.ccore.api.loot.condition.LootTableCheck;
import com.calemi.ccore.main.CCore;
import com.calemi.ccore.main.CCoreRef;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CLootItemConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, CCoreRef.ID);

    public static final Supplier<LootItemConditionType> LOOT_TABLE_CHECK  =
            LOOT_ITEM_CONDITION_TYPES.register("loot_table_check", () -> new LootItemConditionType(LootTableCheck.CODEC));

    public static void init() {
        CCore.LOGGER.info("Registering: Loot Item Conditions - Start");
        LOOT_ITEM_CONDITION_TYPES.register(CCore.MOD_EVENT_BUS);
        CCore.LOGGER.info("Registering: Loot Item Conditions - End");
    }
}