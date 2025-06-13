package com.calemi.ccore.impl.entity;

import com.calemi.ccore.api.entity.CBoat;
import com.calemi.ccore.api.entity.CChestBoat;
import com.calemi.ccore.main.CCore;
import com.calemi.ccore.main.CCoreRef;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, CCoreRef.ID);

    public static final Supplier<EntityType<CBoat>> BOAT = register("boat",
            EntityType.Builder.<CBoat>of(CBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

    public static final Supplier<EntityType<CChestBoat>> CHEST_BOAT = register("chest_boat",
            EntityType.Builder.<CChestBoat>of(CChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> register(String name, EntityType.Builder<E> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(CCoreRef.ID + ":" + name));
    }

    public static void init() {
        CCore.LOGGER.info("Registering: Entity Types - Start");
        ENTITY_TYPES.register(CCore.MOD_EVENT_BUS);
        CCore.LOGGER.info("Registering: Entity Types - End");
    }
}
