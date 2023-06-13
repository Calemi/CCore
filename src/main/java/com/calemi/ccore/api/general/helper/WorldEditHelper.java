package com.calemi.ccore.api.general.helper;

import com.calemi.ccore.api.general.Location;
import com.calemi.ccore.api.shape.ShapeBase;

import java.util.ArrayList;

/**
 * Use this class to help generate complex formations.
 */
public class WorldEditHelper {

    /**
     * @param shape The shape to construct.
     * @return A list of Locations required to build the shape.
     */
    public static ArrayList<Location> selectShape(ShapeBase shape) {
        return shape.getShapeLocations();
    }
}
