package com.calemi.ccore.api.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class ShapeFlatCube implements IShape {

    private final List<BlockPos> shapePositions;

    /**
     * Creates a flat cube Shape.
     * @param facing Determines the direction of the flat cube
     * @param radius The radius of the flat cube.
     */
    public ShapeFlatCube(Direction facing, int radius) {

        shapePositions = new ArrayList<>();

        int xRad = radius;
        int yRad = radius;
        int zRad = radius;

        if (facing == Direction.UP || facing == Direction.DOWN) {
            yRad = 0;
        }

        else if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            zRad = 0;
        }

        else if (facing == Direction.EAST || facing == Direction.WEST) {
            xRad = 0;
        }

        shapePositions.addAll(new ShapeCube(xRad, yRad, zRad).getShapePositions());
    }

    @Override
    public List<BlockPos> getShapePositions() {
        return shapePositions;
    }
}