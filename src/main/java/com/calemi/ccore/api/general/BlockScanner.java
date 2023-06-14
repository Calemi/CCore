package com.calemi.ccore.api.general;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlockState;

import java.util.ArrayList;

/**
 * Used to scan through blocks of the same kind. Collects all scanned blocks in a list.
 */
public class BlockScanner {

    public final ArrayList<Location> buffer = new ArrayList<>();

    public final Location origin;
    public final IForgeBlockState blockToScan;
    public final int maxScanSize;
    public final boolean useDefaultState;

    /**
     * Creates a BlockScanner
     * @param origin The origin Location of the scan.
     * @param blockToScan The Block State to scan for.
     * @param maxScanSize The maximum amount of Blocks to scan.
     * @param useDefaultState If true, will only compare
     */
    public BlockScanner(Location origin, IForgeBlockState blockToScan, int maxScanSize, boolean useDefaultState) {
        this.origin = origin;
        this.blockToScan = blockToScan;
        this.maxScanSize = maxScanSize;
        this.useDefaultState = useDefaultState;
    }

    /**
     * Starts a scan that will search adjacent Blocks.
     */
    public void startVeinScan() {
        reset();
        scan(origin, false);
    }

    /**
     * Starts a scan that will search adjacent Blocks including diagonal ones too.
     */
    public void startRadiusScan() {
        reset();
        scan(origin, true);
    }

    /**
     * Clears the buffer.
     */
    public void reset() {
        buffer.clear();
    }

    /**
     * Recursive method used to search through similar Blocks.
     * @param location The Location to search.
     * @param isRadiusScan If true, the scan will be a Radius scan.
     */
    public void scan(Location location, boolean isRadiusScan) {

        if (buffer.size() >= maxScanSize) {
            return;
        }

        if (!buffer.contains(location) && location.getBlock() != null) {

            BlockState state = useDefaultState ? location.getBlockState().getBlock().defaultBlockState() : location.getBlockState();

            if (state != blockToScan) {
                return;
            }

            buffer.add(location);

            if (isRadiusScan) {

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {

                            Location nextLocation = new Location(location.getLevel(), location.getX() + x, location.getY() + y, location.getZ() + z);
                            scan(nextLocation, isRadiusScan);
                        }
                    }
                }
            }

            else {

                for (Direction dir : Direction.values()) {
                    Location locationCopy = location.copy();
                    locationCopy.relative(dir);
                    scan(locationCopy, isRadiusScan);
                }
            }
        }
    }

    /**
     * @param location The Location to test.
     * @return True, if the given location is in the buffer.
     */
    public boolean contains(Location location) {

        for (Location nextLocation : buffer) {

            if (nextLocation.equals(location)) {
                return true;
            }
        }

        return false;
    }
}
