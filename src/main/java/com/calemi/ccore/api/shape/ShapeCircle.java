package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapeCircle extends ShapeBase {

    /**
     * Creates a circle Shape.
     * @param origin    The origin Location of the circle.
     * @param edge      The edge Location of the circle.
     * @param isHollow  If true, the circle will be hollow on the inside.
     * @param thickness The thickness of the circle's edge. Only used for hollow circles.
     */
    public ShapeCircle(Location origin, Location edge, boolean isHollow, int thickness) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int radius = (int) origin.getDistance(edge);

        for (int x = -radius; x <= radius; x++) {

            for (int z = -radius; z <= radius; z++) {

                Location nextLoc = origin.copy();
                nextLoc.offset(x, 0, z);

                if (origin.getDistance(nextLoc) - 0.2F <= radius) {

                    if (!isHollow || origin.getDistance(nextLoc) - 0.2F >= radius - thickness) {
                        shapeLocations.add(nextLoc);
                    }
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
