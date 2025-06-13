package com.calemi.ccore.api.scanner;

import com.calemi.ccore.api.location.BlockLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * For scanning positions in a branching "ore-vein" shape.
 */
public class VeinBlockScanner extends BlockScanner {

    private BlockState originBlockState;

    /**
     * Creates a VeinBlockScanner
     * @param level The Level to scan in.
     * @param originPosition The BlockPos to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public VeinBlockScanner(Level level, BlockPos originPosition, int maxCollectionSize) {
        super(level, originPosition, maxCollectionSize);
    }

    /**
     * Creates a VeinBlockScanner
     * @param originLocation The BlockLocation to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public VeinBlockScanner(BlockLocation originLocation, int maxCollectionSize) {
        this(originLocation.getLevel(), originLocation.getBlockPos(), maxCollectionSize);
    }

    @Override
    public void start() {
        super.start();
        originBlockState = getLevel().getBlockState(getOriginPosition());
    }

    @Override
    public boolean shouldCollect(BlockPos scannedBlockPos) {
        return getLevel().getBlockState(scannedBlockPos).equals(originBlockState);
    }

    @Override
    public boolean branchOnFailedCollect() {
        return false;
    }

    @Override
    public List<BlockPos> nextPositionsToScan(BlockPos prevBlockPos) {

        List<BlockPos> nextLocations = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {

                    scan(prevBlockPos.offset(x, y, z));
                }
            }
        }

        return List.of();
    }
}
