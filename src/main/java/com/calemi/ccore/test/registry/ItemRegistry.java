package com.calemi.ccore.test.registry;

import com.calemi.ccore.main.CCore;
import com.calemi.ccore.main.CCoreRef;
import com.calemi.ccore.test.item.TestItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, CCoreRef.MOD_ID);

    public static final RegistryObject<Item> TEST = item("test", TestItem::new);

    public static RegistryObject<Item> item(String name, final Supplier<? extends Item> sup) {
        return ITEMS.register(name, sup);
    }

    public static void init() {
        ITEMS.register(CCore.MOD_EVENT_BUS);
    }
}
