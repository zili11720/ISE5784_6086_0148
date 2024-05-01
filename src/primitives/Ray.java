package primitives;

public class Ray {
    final Point head;
    final Vector directions;
    public Ray(Point head, Vector directions) {
        this.head = head;

        this.directions = directions;
    }
}
