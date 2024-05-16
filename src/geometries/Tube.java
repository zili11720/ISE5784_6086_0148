package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 *Represents a tube in 3D which is an infinite galilee
 * @author Ayala
 */
public class Tube extends RadialGeometry{

    /**The central axis ray of the tube*/
    protected final Ray axis;

    /**
     * Constructs a radial geometry with the given radius and axis ray
     * @param radius the radius of the geometry
     * @param axis the central axis ray of the tube
     */
    public Tube(double radius,Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        double t=axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        Point point0=axis.getPoint(t);
        return p.subtract(point0).normalize();
    }

    //BONUS
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point d, e, rayP0 = ray.getHead();
        Vector rayDir = ray.getDirection();
        double dis, ab, bc, ac;

        // initialize the points and vectors of the axis of the cylinder
        Point pointA = rayP0, pointB = axis.getHead();
        Vector vecA = rayDir, vecB = axis.getDirection();

        // calculate dot product between ray direction vector and axis vector
        ab = vecA.dotProduct(vecB);

        // check if the vectors are parallel, if so return null
        try {
            vecA.crossProduct(vecB);
        } catch (IllegalArgumentException ex) {
            return null;
        }

        // calculate values for quadratic equation
        double bb = 1;
        double aa = 1;
        try {
            Vector c = pointB.subtract(pointA);
            bc = vecB.dotProduct(c);
            ac = vecA.dotProduct(c);

            double t1 = (-ab * bc + ac * bb) / (aa * bb - ab * ab);
            try {
                d = pointA.add(vecA.scale(t1));
            } catch (IllegalArgumentException ex) {
                d = pointA;
            }

            double t2 = (ab * ac - bc * aa) / (1 - ab * ab);

            try {
                e = pointB.add(vecB.scale(t2));
            } catch (IllegalArgumentException ex) {
                e = pointB;
            }
            dis = d.distance(e);

        } catch (IllegalArgumentException ex) {
            d = rayP0;
            dis = 0;
        }

        // check if the ray misses the cylinder
        double diff = alignZero(dis - radius);
        if (diff > 0.0)
            return null;

        // check if the ray is tangent to the cylinder
        if (diff == 0.0) {
            return null;
        }

        // calculate the width of the intersection at the intersection point
        double width;
        try {
            double sinA = vecA.crossProduct(vecB).length();
            width = radius / sinA;
        } catch (IllegalArgumentException ex) { // it is orthogonal
            width = radius;
        }
        double k = width / radius;

        // calculate the intersection points
        double th = Math.sqrt(radius * radius - dis * dis) * k;

        Point p1 = d.subtract(vecA.scale(th));
        Point p2 = d.add(vecA.scale(th));

        // check which intersection point is in front of the camera
        try {
            if (!(p1.subtract(pointA).dotProduct(vecA) < 0.0) && !(p2.subtract(pointA).dotProduct(vecA) < 0.0)) {
                return List.of(p1, p2);
            }
        } catch (IllegalArgumentException ex) {
        }

        try {
            if (!(p1.subtract(pointA).dotProduct(vecA) < 0.0)) {
                return List.of(p1);
            }
        } catch (IllegalArgumentException ex) {
        }

        try {
            if (!(p2.subtract(pointA).dotProduct(vecA) < 0.0)) {
                return List.of(p2);
            }
        } catch (IllegalArgumentException ex) {
        }

        return null;
    }
}
