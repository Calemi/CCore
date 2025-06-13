package com.calemi.ccore.api.sound;

import com.calemi.ccore.api.location.BlockLocation;
import com.calemi.ccore.api.math.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Use this class to build a sound and play it.
 */
public class SoundProfile {

    private SoundEvent soundEvent = null;
    private SoundSource soundSource = SoundSource.PLAYERS;
    private Level level = null;
    private Vec3 position = null;

    private Random random = new Random();

    private float minVolume = 1;
    private float maxVolume = 1;
    private float minPitch = 1;
    private float maxPitch = 1;

    private boolean distanceDelay = false;

    /*
        GETTERS
     */

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public SoundSource getSoundSource() {
        return soundSource;
    }

    public Level getLevel() {
        return level;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Random getRandom() {
        return random;
    }

    public float getMinVolume() {
        return minVolume;
    }

    public float getMaxVolume() {
        return maxVolume;
    }

    public float getMinPitch() {
        return minPitch;
    }

    public float getMaxPitch() {
        return maxPitch;
    }

    public float getVolume() {
        if (minVolume == maxVolume) return minVolume;
        return MathHelper.randomRange(minVolume, maxVolume);
    }

    public float getPitch() {
        if (minPitch == maxPitch) return minPitch;
        return MathHelper.randomRange(minPitch, maxPitch);
    }

    public boolean hasDistanceDelay() {
        return distanceDelay;
    }

    /*
        BUILDER
     */

    public SoundProfile setLevel(Level level) {
        this.level = level;
        return this;
    }

    public SoundProfile setLevel(Player player) {
        this.level = player.level();
        return this;
    }

    public SoundProfile setLevel(BlockLocation location) {
        this.level = location.getLevel();
        return this;
    }

    public SoundProfile setPosition(Vec3 position) {
        this.position = position;
        return this;
    }

    public SoundProfile setPosition(Player player) {
        setPosition(player.position());
        return this;
    }

    public SoundProfile setPosition(BlockPos blockPos) {
        this.position = new Vec3(blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F);
        return this;
    }

    public SoundProfile setPosition(BlockLocation location) {
        setPosition(location.getBlockPos());
        return this;
    }

    public SoundProfile setLevelAndPosition(Player player) {
        setLevel(player);
        setPosition(player);
        return this;
    }

    public SoundProfile setLevelAndPosition(BlockLocation location) {
        setLevel(location);
        setPosition(location);
        return this;
    }

    public SoundProfile setEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
        return this;
    }

    public SoundProfile setSource(SoundSource soundSource) {
        this.soundSource = soundSource;
        return this;
    }

    public SoundProfile setRandom(Random random) {
        this.random = random;
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

    public SoundProfile setHasDistanceDelay(boolean distanceDelay) {
        this.distanceDelay = distanceDelay;
        return this;
    }

    /*
        PLAY METHODS
     */

    /**
     * Plays the constructed sound.
     * @param ignorePlayer The player to NOT play the sound to.
     */
    public void play(@Nullable Player ignorePlayer) {
        getLevel().playSound(ignorePlayer, position.x, position.y, position.z, getSoundEvent(), getSoundSource(), getVolume(), getPitch());
    }

    /**
     * Plays the constructed sound.
     */
    public void play() {
        play(null);
    }

    /**
     * Plays the constructed sound to the client player. Only the client player can hear the sound.
     */
    public void playLocal() {
        getLevel().playLocalSound(position.x, position.y, position.z, getSoundEvent(), getSoundSource(), getVolume(), getPitch(), distanceDelay);
    }

    /**
     * Plays the constructed sound.
     * Must be called in a server context only. The sound can only be heard by the passed-in player and no one else.
     * @param player The Player to play the sound to.
     */
    public void playPacket(ServerPlayer player) {
        player.connection.send(new ClientboundSoundPacket(Holder.direct(getSoundEvent()), getSoundSource(), position.x, position.y, position.z, getVolume(), getPitch(), random.nextLong()));
    }

    /**
     * Plays the constructed sound to everyone in the Level.
     */
    public void playGlobal() {

        for (Player player : level.players()) {
            playLocal();
        }
    }
}