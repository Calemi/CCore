package com.calemi.ccore.api.general.helper;

import com.calemi.ccore.api.general.Location;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

/**
 * Used to help with placing Blocks.
 */
public class BlockHelper {

    /**
     * Places a block in the next available place in a row.
     * @param block         The Block that gets placed.
     * @param location      The origin of the search.
     * @param direction     The Direction of the row.
     * @param maxSearchSize The maximum amount of Blocks that will be placed.
     * @return True, if a block has been placed.
     */
    public static boolean placeBlockInLine(Block block, Location location, Direction direction, int maxSearchSize) {

        int count = 0;

        //Iterates through every possible placement in a line.
        for (int i = 0; i < maxSearchSize; i++) {

            Location nextLocation = location.copy();
            nextLocation.relative(direction, i);

            //Keep searching until a different block is found;
            if (nextLocation.getBlock() == block) {
                continue;
            }

            if (!nextLocation.isBlockValidForPlacing()) {
                return false;
            }

            nextLocation.setBlockWithUpdate(block);
            SoundHelper.playBlockPlace(nextLocation, nextLocation.getBlockState());

            return true;
        }

        return false;
    }
}
