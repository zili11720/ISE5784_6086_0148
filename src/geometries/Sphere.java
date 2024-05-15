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

        // If the beginning point of the ray is on the sphere center, return the point on the sphere's radius
        if (ray.getHead().equals(center))
            return List.of(ray.getPoint(radius));

        Vector u = center.subtract(ray.getHead());
        double tM = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tM * tM));
        double tH = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tM + tH);
        double t2 = alignZero(tM - tH);

        // If there are no intersections, return null
        if (d >= radius)
            return null;

        if (t1 <= 0 && t2 <= 0)
            return null;

        // If there are two intersections, return them as a list
        if (t1 > 0 && t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));

        // If there is one intersection, return it as a list
        if (t1 > 0)
            return List.of(ray.getPoint(t1));
        else
            return List.of(ray.getPoint(t2));
    }
}
