package com.calemi.ccore.mixin;

import com.calemi.ccore.api.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityDataSaverMixin implements IEntityDataSaver {

    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {

        if (persistentData == null) {
            persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {

        if (persistentData != null) {
            nbt.put("CCoreData", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {

        if (nbt.contains("CCoreData", 10)) {
            persistentData = nbt.getCompound("CCoreData");
        }
    }
}
