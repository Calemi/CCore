package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapeSphere extends ShapeBase {

    /**
     * Creates a sphere Shape.
     * @param origin The origin Location of the sphere.
     * @param edge The edge Location of the sphere.
     * @param isHollow If true, the sphere will be hollow on the inside.
     * @param thickness The thickness of the sphere's edge. Only used for hollow sphere.
     */
    public ShapeSphere(Location origin, Location edge, boolean isHollow, int thickness) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int radius = (int) origin.getDistance(edge);

        for (int x = -radius; x <= radius; x++) {

            for (int y = -radius; y <= radius; y++) {

                for (int z = -radius; z <= radius; z++) {

                    int rx = origin.getX() + x;
                    int ry = origin.getY() + y;
                    int rz = origin.getZ() + z;

                    Location nextLoc = new Location(origin.getLevel(), rx, ry, rz);

                    if (origin.getDistance(nextLoc) - 0.2F <= radius) {

                        if (!isHollow || origin.getDistance(nextLoc) - 0.2F >= radius - thickness) {
                            shapeLocations.add(nextLoc);
                        }
                    }
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
