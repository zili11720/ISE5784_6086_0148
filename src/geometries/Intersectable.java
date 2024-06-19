package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable abstarct class represents any object in the scene that can be
 * intersected by a ray.
 * This interface provides a method to find intersections between a given ray
 * and the object.
 * @author Ayala
 */
public abstract class Intersectable {
    /**
     * Returns a list of intersection points between a given ray and the object.
     * If no intersections are found, null is returned.
     * @param ray The ray to intersect with the object
     * @return A list of intersection points between the ray and the object, or
     * null if no intersections are found
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Represents a geographic point with associated geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry of the point.
         */
        public Geometry geometry;
        /**
         * The point coordinates.
         */
        public Point point;

        /**
         * Constructs a GeoPoint object with the specified geometry and point coordinates
         * @param geometry The geometry of the point.
         * @param point    The point coordinates.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof GeoPoint geoP)) return false;
            return this.geometry== geoP.geometry && point.equals(geoP.point);
        }

        @Override
        public String toString() {
            return "GeoPoint " + "geometry=" + geometry + ", point=" + point ;
        }
    }

    /**
     * Returns a list of intersection points between a given ray and the object
     * @param ray The ray to intersect with the GeoPoint.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Returns a list of intersection points between a given ray and the object. This
     * Method should be implemented by the subclasses to provide their own intersection logic.
     * @param ray The ray to intersect with the object.
     * @return A list of GeoPoints that are the intersections of the ray with the  object
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}