package geometries;

import primitives.Point;
import primitives.Vector;

/**
 *An interface for a 3D Geometry
 *@author zili
 */
public interface Geometry {
    /**
     * Returns normalized vector to a geometry at a given point
     * @param point the point where the normal is required
     * @return  normalized vector to a geometry at a given point
     */
    public Vector getNormal(Point point);

}
