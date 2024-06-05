package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 *Represents a sphere in 3D, which is a ball floating in the space
 * @author zili
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead();

        // If the beginning point of the ray is on the sphere center, return the point
        // on the sphere's radius
        if (p0.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector u = center.subtract(p0);
        double tM = alignZero(ray.getDirection().dotProduct(u));
        double d2 = u.lengthSquared() - tM * tM; // squared d
        double delta2 = alignZero(radius*radius - d2);

        // If there are no intersections, return null
        if (delta2 <= 0)
            return null;

        double tH = Math.sqrt(delta2);

        double t2 = alignZero(tM + tH);
        if (t2 <= 0)
            return null;

        double t1 = alignZero(tM - tH);
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) // P2 only
                : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))); // P1 & P2
    }
}
