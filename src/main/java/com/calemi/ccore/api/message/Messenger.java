package com.calemi.ccore.api.message;

import com.calemi.ccore.api.string.StringHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record Messenger(String name) {

    public MutableComponent getBoxedName() {
        return Component.literal(StringHelper.boxString(name()));
    }

    public void sendGlobal(MutableComponent message, Level level) {
        send(message, level.players().toArray(Entity[]::new));
    }

    public void send(MutableComponent message, @Nullable Entity... recipients) {

        MutableComponent fullMessage = getBoxedName().append(" ").append(message);

        for (Entity entity : recipients) {
            if (entity != null) entity.sendSystemMessage(fullMessage);
        }
    }
}
