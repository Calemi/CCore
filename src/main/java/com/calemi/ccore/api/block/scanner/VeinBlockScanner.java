package com.calemi.ccore.api.block.scanner;

import com.calemi.ccore.api.location.BlockLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * For scanning positions in a branching "ore-vein" shape.
 */
public class VeinBlockScanner extends BlockScanner {

    private Block originBlock;

    /**
     * Creates a VeinBlockScanner
     * @param level The Level to scan in.
     * @param originPosition The BlockPos to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public VeinBlockScanner(Level level, BlockPos originPosition, int maxCollectionSize) {
        super(level, originPosition, maxCollectionSize);
        originBlock = getLevel().getBlockState(getOriginPosition()).getBlock();
    }

    /**
     * Creates a VeinBlockScanner
     * @param originLocation The BlockLocation to start the scan at.
     * @param maxCollectionSize The maximum amount of Blocks to collect.
     */
    public VeinBlockScanner(BlockLocation originLocation, int maxCollectionSize) {
        this(originLocation.getLevel(), originLocation.getBlockPos(), maxCollectionSize);
        originBlock = originLocation.getBlockState().getBlock();
    }

    @Override
    public boolean shouldCollect(BlockPos scannedBlockPos) {
        return getLevel().getBlockState(scannedBlockPos).getBlock().equals(originBlock);
    }

    @Override
    public boolean branchOnFailedCollect() {
        return false;
    }

    @Override
    public List<BlockPos> nextPositionsToScan(BlockPos prevBlockPos) {

        List<BlockPos> nextPositions = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {

                    nextPositions.add(prevBlockPos.offset(x, y, z));
                }
            }
        }

        return nextPositions;
    }
}
