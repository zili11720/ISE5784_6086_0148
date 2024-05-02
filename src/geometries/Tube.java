package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *Represents a tube in 3D which is an infinite galilee
 * @author ayala
 */
public class Tube extends RadialGeometry{

    /**The central axis ray of the tube*/
    protected Ray axis;

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
