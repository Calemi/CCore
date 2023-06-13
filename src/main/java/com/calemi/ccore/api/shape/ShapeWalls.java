package com.calemi.ccore.api.shape;

import com.calemi.ccore.api.general.Location;

import java.util.ArrayList;

public class ShapeWalls extends ShapeBase {

    /**
     * Creates a walls Shape.
     * @param origin The origin Location of the walls.
     * @param radius The radius of the walls.
     * @param y1 The first y coordinate of the walls. Interchangeable. Used to determine height.
     * @param y2 The second y coordinate of the walls. Interchangeable. Used to determine height.
     */
    public ShapeWalls(Location origin, int radius, int y1, int y2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        if (radius < 0) return;

        else if (radius == 0) {

            for (int y = minY; y <= maxY; y++) {
                shapeLocations.add(new Location(origin.getLevel(), origin.getX(), y, origin.getZ()));
            }
        }

        else {

            for (int i = -radius; i <= radius; i++) {

                for (int y = minY; y <= maxY; y++) {
                    shapeLocations.add(new Location(origin.getLevel(), origin.getX() + radius, y, origin.getZ() + i));
                    shapeLocations.add(new Location(origin.getLevel(), origin.getX() - radius, y, origin.getZ() + i));
                }
            }

            for (int i = -radius + 1; i <= radius - 1; i++) {

                for (int y = minY; y <= maxY; y++) {
                    shapeLocations.add(new Location(origin.getLevel(), origin.getX() + i, y, origin.getZ() + radius));
                    shapeLocations.add(new Location(origin.getLevel(), origin.getX() + i, y, origin.getZ() - radius));
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
