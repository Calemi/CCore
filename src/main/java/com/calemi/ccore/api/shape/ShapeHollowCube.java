package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapeHollowCube extends ShapeBase {

    /**
     * Creates a hollow cube Shape.
     * @param corner1 The first corner Location of the hollow cube.
     * @param corner2 The second corner Location of the hollow cube.
     */
    public ShapeHollowCube(Location corner1, Location corner2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        //X Walls
        for (int x = Math.min(corner1.getX(), corner2.getX()); x <= Math.max(corner1.getX(), corner2.getX()); x++) {

            for (int y = Math.min(corner1.getY(), corner2.getY()); y <= Math.max(corner1.getY(), corner2.getY()); y++) {
                shapeLocations.add(new Location(corner1.getLevel(), x, y, Math.min(corner1.getZ(), corner2.getZ())));
                shapeLocations.add(new Location(corner1.getLevel(), x, y, Math.max(corner1.getZ(), corner2.getZ())));
            }
        }

        //Z Walls
        for (int z = Math.min(corner1.getZ(), corner2.getZ()) + 1; z <= Math.max(corner1.getZ(), corner2.getZ()) - 1; z++) {

            for (int y = Math.min(corner1.getY(), corner2.getY()); y <= Math.max(corner1.getY(), corner2.getY()); y++) {
                shapeLocations.add(new Location(corner1.getLevel(), Math.min(corner1.getX(), corner2.getX()), y, z));
                shapeLocations.add(new Location(corner1.getLevel(), Math.max(corner1.getX(), corner2.getX()), y, z));
            }
        }

        //Top and Bottom Walls
        for (int x = Math.min(corner1.getX(), corner2.getX()) + 1; x <= Math.max(corner1.getX(), corner2.getX()) - 1; x++) {

            for (int z = Math.min(corner1.getZ(), corner2.getZ()) + 1; z <= Math.max(corner1.getZ(), corner2.getZ()) - 1; z++) {
                shapeLocations.add(new Location(corner1.getLevel(), x, Math.min(corner1.getY(), corner2.getY()), z));
                shapeLocations.add(new Location(corner1.getLevel(), x, Math.max(corner1.getY(), corner2.getY()), z));
            }
        }

        addShapeLocations(shapeLocations);
    }
}
