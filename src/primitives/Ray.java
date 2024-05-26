package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space,defined by a starting point and a normalized vector for the direction
 * @author Zili
 */
public class Ray {
    /**The starting point of the ray*/
    private final Point head;
    /**The direction of the ray*/
    private final Vector direction;

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
        if (points.isEmpty())
            return null;
        double minDistance = Double.POSITIVE_INFINITY;
        Point closest = null;
        for (var p : points) {
            double distance = head.distance(p);
            if (distance <  minDistance) {
                minDistance = distance;
                closest = p;
            }
        }
        return closest;
    }
}
