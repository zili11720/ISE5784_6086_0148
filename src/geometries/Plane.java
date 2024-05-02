package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The class represents a plane in a 3D space
 */
public class Plane implements  Geometry {
    /** A point on the plane */
   private Point q;
    /** A normal vector to the plane */
   private Vector normal;

    /**
     * Constructs the plane using one of the given points and null for the normal vector
     * @param p1 a point on the plane
     * @param p2 a point on the plane
     * @param p3 a point on the plane
     */
   Plane(Point p1, Point p2,Point p3) {
       this.q=p1;
       this.normal=null;
   }
    /**
     * Constructs a new plane according to a given point and a given vector after normalizing the vector
     * @param p1 the point on the plane
     * @param vec the normal vector to the plane
     */
    Plane(Point p1, Vector vec) {
        q = p1;
        normal = vec.normalize();
    }

    /**
     * Returns the normal vector to the plane.
     * @return the normal vector to the plane
     */
    public Vector getNormal() {
        return null;
    }

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }
}
