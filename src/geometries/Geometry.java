package geometries;

import primitives.Point;
import primitives.Vector;

/**
 *
 */
public interface Geometry {
    /**
     * Returns normalized vector to a geometry at a given point
     * @param point to calculate the normal
     * @return  normalized vector to a geometry at a given point
     */
    public Vector getNormal(Point point);

}
