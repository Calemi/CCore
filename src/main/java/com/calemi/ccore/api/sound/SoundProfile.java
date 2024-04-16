package com.calemi.ccore.api.sound;

import com.calemi.ccore.api.general.CCoreMathHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import java.util.Random;

public class SoundProfile {

    private final Random random = new Random();

    private final SoundEvent soundEvent;
    private SoundCategory soundCategory = SoundCategory.AMBIENT;
    private float minVolume = 1;
    private float maxVolume = 1;
    private float minPitch = 1;
    private float maxPitch = 1;

    public SoundProfile(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    public SoundProfile setCategory(SoundCategory soundCategory) {
        this.soundCategory = soundCategory;
        return this;
    }

    public SoundProfile setVolume(float minVolume, float maxVolume) {
        this.minVolume = minVolume;
        this.maxVolume = maxVolume;
        return this;
    }

    public SoundProfile setVolume(float volume) {
        return setVolume(volume, volume);
    }

    public SoundProfile setPitch(float minPitch, float maxPitch) {
        this.minPitch = minPitch;
        this.maxPitch = maxPitch;
        return this;
    }

    public SoundProfile setPitch(float pitch) {
        return setPitch(pitch, pitch);
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public float getVolume() {
        if (minVolume == maxVolume) return minVolume;
        return CCoreMathHelper.randomRange(minVolume, maxVolume);
    }

    public float getPitch() {
        if (minPitch == maxPitch) return minPitch;
        return CCoreMathHelper.randomRange(minPitch, maxPitch);
    }
}
