package com.calemi.ccore.api.general.helper;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Use this class to help with Mob Effects.
 */
public class MobEffectHelper {

    /**
     * Applies an effect to Entities.
     * @param effect        The effect to add.
     * @param durationTicks The duration in ticks of the effect.
     * @param amplifier     The power of the effect. Starts at 0.
     * @param entities      The entities that will receive the effect.
     */
    public static void addMobEffect(MobEffect effect, int durationTicks, int amplifier, LivingEntity... entities) {

        for (LivingEntity entity : entities) {

            if (entity == null) {
                continue;
            }

            if (!(entity instanceof Player) || !((Player) entity).isCreative()) {

                MobEffectInstance effectInstance = entity.getEffect(effect);

                if (effectInstance == null) {
                    entity.addEffect(new MobEffectInstance(effect, durationTicks, amplifier, true, false));
                }

                else if (amplifier > effectInstance.getAmplifier() || (amplifier == effectInstance.getAmplifier() && durationTicks > effectInstance.getDuration())) {
                    entity.forceAddEffect(new MobEffectInstance(effect, durationTicks, amplifier, true, false), entity);
                }
            }
        }
    }

    /**
     * Applies an infinite effect to Entities. Infinite effects last until they are removed.
     * @param effect    The effect to add.
     * @param amplifier The power of the effect. Starts at 0.
     * @param entities  The entities that will receive the effect.
     */
    public static void addInfiniteMobEffect(MobEffect effect, int amplifier, LivingEntity... entities) {
        addMobEffect(effect, 1000000, amplifier, entities);
    }
}
