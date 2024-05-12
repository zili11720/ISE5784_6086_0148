package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *Represents a tube in 3D which is an infinite galilee
 * @author Ayala
 */
public class Tube extends RadialGeometry{

    /**The central axis ray of the tube*/
    protected final Ray axis;

    /**
     * Constructs a radial geometry with the given radius and axis ray
     * @param radius the radius of the geometry
     * @param axis the central axis ray of the tube
     */
    public Tube(double radius,Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        double t=axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        Point point0 =axis.getHead().add(axis.getDirection().scale(t));
        return p.subtract(point0).normalize();
    }
}
