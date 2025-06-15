package com.calemi.ccore.api.block.scanner;

import com.calemi.ccore.api.location.BlockLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to scan through blocks of the same kind. Collects all scanned blocks in a list.
 */
public abstract class BlockScanner {

    private final ArrayList<BlockPos> collectedPositions = new ArrayList<>();

    private final Level level;
    private final BlockPos originPosition;
    private final int maxCollectionSize;
    private boolean halted = false;

    /**
     * Creates a BlockScanner
     * @param level The Level to scan in.
     * @param originPosition The BlockPos to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public BlockScanner(Level level, BlockPos originPosition, int maxCollectionSize) {
        this.level = level;
        this.originPosition = originPosition;
        this.maxCollectionSize = maxCollectionSize;
    }

    /**
     * Creates a BlockScanner
     * @param originLocation The Location to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public BlockScanner(BlockLocation originLocation, int maxCollectionSize) {
        this(originLocation.getLevel(), originLocation.getBlockPos(), maxCollectionSize);
    }

    public ArrayList<BlockPos> getCollectedPositions() {
        return collectedPositions;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getOriginPosition() {
        return originPosition;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public boolean isHalted() {
        return halted;
    }

    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    /**
     * @param scannedBlockPos The currently scanned BlockPos.
     * @return whether the scanner should collect this BlockPos or not.
     */
    public abstract boolean shouldCollect(BlockPos scannedBlockPos);

    /**
     * @return whether the scanner should scan further BlockPos from a previously scanned BlockPos if it failed to collect it.
     */
    public abstract boolean branchOnFailedCollect();

    /**
     * @return the next BlockPos to scan.
     */
    public abstract List<BlockPos> nextPositionsToScan(BlockPos prevBlockPos);

    /**
     * Starts a scan that will search adjacent Blocks.
     */
    public void start() {
        reset();
        scan(originPosition);
    }

    /**
     * Clears the buffer.
     */
    public void reset() {
        collectedPositions.clear();
        halted = false;
    }

    /**
     * Recursive method used to search through similar Blocks.
     * @param location The Location to search.
     */
    public void scan(BlockPos location) {

        if (halted || collectedPositions.size() >= maxCollectionSize) {
            return;
        }

        if (!collectedPositions.contains(location)) {

            if (shouldCollect(location)) {
                collectedPositions.add(location);
            }

            else if (!branchOnFailedCollect()) {
                return;
            }

            for (BlockPos nextPositionToScan : nextPositionsToScan(location)) {
                scan(nextPositionToScan);
            }
        }
    }
}