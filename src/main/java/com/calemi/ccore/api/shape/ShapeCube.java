package com.calemi.ccore.api.shape;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ShapeCube implements IShape {

    private final List<BlockPos> shapePositions;

    /**
     * Creates a cube Shape.
     * Both parameters must be offsets. Not actual positions.
     * @param corner1 The first corner BlockPos of the cube.
     * @param corner2 The second corner BlockPos of the cube.
     */
    public ShapeCube(BlockPos corner1, BlockPos corner2) {

        shapePositions = new ArrayList<>();

        for (int x = Math.min(corner1.getX(), corner2.getX()); x <= Math.max(corner1.getX(), corner2.getX()); x++) {

            for (int y = Math.min(corner1.getY(), corner2.getY()); y <= Math.max(corner1.getY(), corner2.getY()); y++) {

                for (int z = Math.min(corner1.getZ(), corner2.getZ()); z <= Math.max(corner1.getZ(), corner2.getZ()); z++) {

                    shapePositions.add(new BlockPos(x, y, z));
                }
            }
        }
    }

    /**
     * Creates a cube Shape.
     * @param xRadius The x radius of the cube.
     * @param yRadius The y radius of the cube.
     * @param zRadius The z radius of the cube.
     */
    public ShapeCube(int xRadius, int yRadius, int zRadius) {

        shapePositions = new ArrayList<>();

        for (int x = -xRadius; x <= xRadius; x++) {

            for (int y = -yRadius; y <= yRadius; y++) {

                for (int z = -zRadius; z <= zRadius; z++) {

                    shapePositions.add(new BlockPos(x, y, z));
                }
            }
        }
    }

    @Override
    public List<BlockPos> getShapePositions() {
        return shapePositions;
    }
}