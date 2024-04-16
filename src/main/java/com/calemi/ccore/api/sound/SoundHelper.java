package com.calemi.ccore.api.sound;

import com.calemi.ccore.api.location.Location;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Use this class to play sounds.
 */
public class SoundHelper {

    /**
     * Plays a global sound that everyone in the Level can hear.
     * @param level The level to play in.
     * @param profile The Sound Profile to get values from.
     */
    public static void playGlobal(World level, SoundProfile profile) {

        for (PlayerEntity player : level.getPlayers()) {
            playAtLocationLocal(new Location(player), profile);
        }
    }

    /**
     * Plays a sound at a Location.
     * @param location The Location to play the sound at.
     * @param profile The Sound Profile to get values from.
     */
    public static void playAtLocation(Location location, SoundProfile profile) {
        location.getWorld().playSound(null, location.getBlockPos(), profile.getSoundEvent(), profile.getSoundCategory(), profile.getVolume(), profile.getPitch());
    }

    /**
     * Plays a sound at a Location on client-side:
     * @param location The Location to play the sound at.
     * @param profile The Sound Profile to get values from.
     */
    public static void playAtLocationLocal(Location location, SoundProfile profile) {
        location.getWorld().playSound(location.getX(), location.getY(), location.getZ(), profile.getSoundEvent(), profile.getSoundCategory(), profile.getVolume(), profile.getPitch(), false);
    }

    /**
     * Plays a sound at a Player.
     * @param player The Player to play the sound at.
     * @param profile The Sound Profile to get values from.
     */
    public static void playAtPlayer(PlayerEntity player, SoundProfile profile) {
        player.getWorld().playSound(null, new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), profile.getSoundEvent(), profile.getSoundCategory(), profile.getVolume(), profile.getPitch());
    }

    /**
     * Players a Block's placing sound at a Location.
     * @param location The Location to play the sound at.
     * @param state The BlockState of the placed Block.
     */
    public static void playBlockPlace(Location location, BlockState state) {

        SoundProfile profile = new SoundProfile(state.getSoundGroup().getPlaceSound());
        profile.setCategory(SoundCategory.BLOCKS);
        profile.setVolume(1);
        profile.setPitch(1);

        playAtLocation(location, profile);
    }

    /**
     * Players a Block's break sound at a Location.
     * @param location The Location to play the sound at.
     * @param state The BlockState of the broke Block.
     */
    public static void playBlockBreak(Location location, BlockState state) {

        SoundProfile profile = new SoundProfile(state.getSoundGroup().getBreakSound());
        profile.setCategory(SoundCategory.BLOCKS);
        profile.setVolume(1);
        profile.setPitch(1);

        playAtLocation(location, profile);
    }
}
