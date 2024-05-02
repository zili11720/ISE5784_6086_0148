package geometries;

import primitives.Point;
import primitives.Vector;

/**
 *Represents a sphere in 3D, which is a ball floating in the space
 * @author ayala
 */
public class Sphere extends RadialGeometry {

    /**The center point of the sphere*/
    private Point center;

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
