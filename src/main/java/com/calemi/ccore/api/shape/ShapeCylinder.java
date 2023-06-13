package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapeCylinder extends ShapeBase {

    /**
     * Creates a cylinder Shape.
     * @param origin The origin Location of the cylinder.
     * @param edge The edge Location of the cylinder.
     * @param isHollow If true, the cylinder will be hollow on the inside.
     * @param thickness The thickness of the cylinder's edge. Only used for hollow cylinder.
     */
    public ShapeCylinder(Location origin, Location edge, boolean isHollow, int thickness) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int minY = Math.min(origin.getY(), edge.getY());
        int maxY = Math.max(origin.getY(), edge.getY());

        for (int y = minY; y <= maxY; y++) {
            shapeLocations.addAll(new ShapeCircle(new Location(origin.getLevel(), origin.getX(), y, origin.getZ()), new Location(edge.getLevel(), edge.getX(), y, edge.getZ()), isHollow, thickness).getShapeLocations());
        }

        addShapeLocations(shapeLocations);
    }
}
