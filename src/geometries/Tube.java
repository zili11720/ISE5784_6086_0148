package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector dir = ray.getDirection();
        Vector v = axis.getDirection();
        double dirV = dir.dotProduct(v);

        if (ray.getHead().equals(axis.getHead())) { // In case the ray starts on the p0.
            if (isZero(dirV))
                return List.of(new GeoPoint(this, ray.getPoint(radius)));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(new GeoPoint(this, ray
                    .getPoint(Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v))).lengthSquared()))));
        }

        Vector deltaP = ray.getHead().subtract(axis.getHead());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<GeoPoint> _points = new ArrayList<>(2);
            _points.add(new GeoPoint(this, ray.getPoint(t1)));
            _points.add(new GeoPoint(this, ray.getPoint(t2)));
            return _points;
        } else if (t1 > 0) {
            List<GeoPoint> _points = new ArrayList<>(1);
            _points.add(new GeoPoint(this, ray.getPoint(t1)));
            return _points;
        } else if (t2 > 0) {
            List<GeoPoint> _points = new ArrayList<>(1);
            _points.add(new GeoPoint(this, ray.getPoint(t2)));
            return _points;
        }
        return null;
    }
}
