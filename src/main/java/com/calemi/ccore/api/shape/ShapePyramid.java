package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapePyramid extends ShapeBase {

    /**
     * Creates a pyramid Shape.
     * @param origin The origin Location of the pyramid.
     * @param radius The radius of the pyramid.
     * @param isHollow If true, the pyramid will be hollow on the inside.
     */
    public ShapePyramid(Location origin, int radius, boolean isHollow) {

        ArrayList<Location> shapeLocations = new ArrayList<>(new ShapeCube(origin, radius, 0, radius).getShapeLocations());

        for (int y = 1; y <= radius; y++) {

            if (isHollow) shapeLocations.addAll(new ShapeWalls(new Location(origin.getLevel(), origin.getX(), origin.getY(), origin.getZ()), radius - y, origin.getY() + y, origin.getY() + y).getShapeLocations());
            else shapeLocations.addAll(new ShapeCube(new Location(origin.getLevel(), origin.getX(), origin.getY() + y, origin.getZ()), radius - y, 0, radius - y).getShapeLocations());
        }

        addShapeLocations(shapeLocations);
    }
}
