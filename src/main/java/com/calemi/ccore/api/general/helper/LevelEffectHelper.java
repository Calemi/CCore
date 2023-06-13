package com.calemi.ccore.api.general.helper;

import com.calemi.ccore.api.general.Location;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;

/**
 * Use this class to help with Level Effects.
 */
public class LevelEffectHelper {

    /**
     * Summons a bolt of lightning at a Location
     * @param location   The Location to summon at.
     * @param visualOnly Does the lightning bolt cause no harm?
     */
    public static void spawnLightning(Location location, boolean visualOnly) {

        if (!location.getLevel().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) location.getLevel();

            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, location.getLevel());
            bolt.setPos(location.getX() + 0.5D, location.getY(), location.getZ() + 0.5D);
            bolt.setVisualOnly(visualOnly);
            serverLevel.addFreshEntity(bolt);
        }
    }

    /**
     * Starts rain in the current Level.
     * @param level         The Level to start rain in.
     * @param durationTicks The duration of the rain in ticks.
     * @param isStorm       Is the rain also a storm?
     */
    public static void startRain(Level level, int durationTicks, boolean isStorm) {

        if (!level.isClientSide()) {

            ServerLevel serverWorld = (ServerLevel) level;
            serverWorld.setWeatherParameters(0, durationTicks, true, isStorm);
        }
    }
}
