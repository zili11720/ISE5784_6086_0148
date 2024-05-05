package geometries;

import primitives.Point;

/**
 *Represents a triangle in 3D space, defined by three points
 * @author Ayala
 */
public class Triangle extends Polygon{
    /**
     * Constructs a new triangle according to three vertices
     * @param p1 first vertex of the triangle
     * @param p2 second vertex of the triangle
     * @param p3 third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) { super(p1,p2,p3);}

}
