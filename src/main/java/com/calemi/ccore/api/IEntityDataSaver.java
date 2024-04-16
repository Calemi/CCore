package com.calemi.ccore.api;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {

    NbtCompound getPersistentData();
}
