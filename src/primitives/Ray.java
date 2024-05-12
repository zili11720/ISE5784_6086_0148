package primitives;

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
}
