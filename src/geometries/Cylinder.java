package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *Represents a cylinder in 3D space, which is a finite tube
 * @author Zili
 */
public class Cylinder extends Tube{

    /**The height of the cylinder*/
    private final double height;

    /**
     * Constructs a radial geometry with the given radius ,axis ray and height
     * @param radius the radius of the geometry
     * @param axis   the central axis ray of the tube
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }


    @Override
    public Vector getNormal(Point p) {
        return null;
    }

}
