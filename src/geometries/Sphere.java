package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 *Represents a sphere in 3D, which is a ball floating in the space
 * @author Ayala
 */
public class Sphere extends RadialGeometry {

    /**The center point of the sphere*/
    protected final Point center;

    /**
     * Constructs a radial geometry with the given radius and center point
     * @param radius the radius of the geometry
     * @param center the center point of the sphere
     */
    public Sphere(double radius,Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p)  {return (p.subtract(center)).normalize();}


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
