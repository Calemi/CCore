package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.location.BlockLocation;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Base interface for a Shape.
 */
public interface IShape {

    /**
     * @return A list of Locations required to build the Shape.
     */
    List<BlockPos> getShapePositions();

    /**
     * @param centerPosition The BlockPos to center the shape at.
     * @return A list of world position that make up the shape.
     */
    default List<BlockPos> getWorldPositions(BlockPos centerPosition) {
        List<BlockPos> positions = new ArrayList<>();
        getShapePositions().forEach(blockPos -> positions.add(blockPos.offset(centerPosition)));
        return positions;
    }

    /**
     * @param centerLocation The BlockLocation to center the shape at.
     * @return A list of world position that make up the shape.
     */
    default List<BlockPos> getWorldPositions(BlockLocation centerLocation) {
        List<BlockPos> positions = new ArrayList<>();
        getShapePositions().forEach(blockPos -> positions.add(blockPos.offset(centerLocation.getBlockPos())));
        return positions;
    }
}