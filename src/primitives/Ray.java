package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;

/**
 * Represents a ray in 3D space,defined by a starting point and a normalized vector for the direction
 * @author Zili
 */
public class Ray {

    /**The starting point of the ray*/
    private final Point head;
    /**The direction of the ray*/
    private final Vector direction;

    private static final double DELTA = 0.1;

    public Point getHead() {
        return head;
    }
    public Vector getDirection() {
        return direction;
    }

    /**
     * Constructs a ray according to a given starting point a vector for the direction
     * @param head the starting point of the ray
     * @param direction a vector for the direction
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Constructs a ray with a given starting point, direction, and surface normal.
     * @param head The starting point of the ray.
     * @param direction The direction of the ray (must be already normalized)
     * @param normal The surface normal at the starting point.
     */
    public Ray(Point head, Vector direction, Vector normal) {
        //keep Don't Repeat Yourself principal
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA);
        this.head = head.add(delta);
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return head+" "+direction;
    }

    /**
     * The method returns a point on the current ray starting
     * from the ray's head + t unites in the direction of the ray
     * @param t A scalar for number of unites to go in the direction of the ray
     * @return a point on the ray
     */
    public Point getPoint(double t) {
        try {
            if (isZero(t))
                return head;
            return head.add(direction.scale(t));
        } catch (Exception e) {
            return head;
        }
    }

    /**
     * Returns the closest point to the head of the ray
     * @param points a list of given points
     * @return the closest point to the head of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     *
     * Finds the closest intersection GeoPoint with a list of GeoPoints.
     *
     * @param points the list of GeoPoints to find the closest intersection with
     * @return the closest intersection GeoPoint, or {@code null} if the list is
     *         null or empty
     */
    public Intersectable.GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> points) {
        if (points == null || points.isEmpty())
            return null;
        Intersectable.GeoPoint closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Intersectable.GeoPoint p : points) {
            double distance = p.point.distance(head);
            if (distance < minDistance) {
                closest = p;
                minDistance = distance;
            }
        }
        return closest;
    }

}
