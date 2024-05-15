package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a plane in a 3D space, which is an infinite flat surface
 * @author Ayala
 */
public class Plane implements Geometry  {

    /** A point on the plane */
   private final Point p0;

    /** A normal vector to the plane */
   private final Vector normal;

    /**
     * Constructs the plane using one of the given points and null for the normal vector
     * @param p1 a point on the plane
     * @param p2 a point on the plane
     * @param p3 a point on the plane
     */
   public Plane(Point p1, Point p2,Point p3) {
       this.p0=p1;
       Vector U = p2.subtract(p1);
       Vector V = p3.subtract(p1);
       Vector N = U.crossProduct(V);
       normal = N.normalize();
   }
    /**
     * Constructs a new plane according to a given point and a given vector after normalizing the vector
     * @param p1 the point on the plane
     * @param vec the normal vector to the plane
     */
    Plane(Point p1, Vector vec) {
        p0 = p1;
        normal = vec.normalize();
    }

    /**
     * Returns the normal vector to the plane.
     * @return the normal vector to the plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Calculate the dot product of the plane's normal vector with the ray's direction vector
        double nv = normal.dotProduct(ray.getDirection());
        if (isZero(nv)) { // if the dot product is zero, the ray is parallel to the plane and doesn't
            // intersect
            return null;
        }
        try {
            // Calculate the parameter t at which the ray intersects the plane
            Vector pSubtractP0 = p0.subtract(ray.getHead());
            double t = alignZero((normal.dotProduct(pSubtractP0)) / nv);
            if (t <= 0) { // if t is negative or zero, the intersection point is behind the ray's origin
                return null;
            }
            // Return a list containing the intersection point
            return List.of(ray.getPoint(t));
        } catch (Exception ex) { // if an exception occurs during the calculation, return null
            return null;
        }
    }
}
