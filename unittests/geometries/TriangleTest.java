package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 * @author  Ayala
 */
public class TriangleTest {

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // =============== Equivalence Partitions Tests ==============
        // TC01: simple check
        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
        Vector expectedNormal=triangle.getNormal(new Point(0, 1, 0));
        assertTrue(new Vector(0, 0, 1).equals(expectedNormal)|| new Vector(0, 0, -1).equals(expectedNormal),"ERROR: Wrong normal calculation for a triangle");
    }

    /**
     * Test method for
     * {@link geometries.Polygon#findIntersections(primitives.Ray)}. /////
     */
    @Test
    public void testFindIntersections() {

        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0));

        // ============ Equivalence Partitions Tests ====================

        // TC01: The ray does not intersect the triangle:
        Ray ray = new Ray(new Point(6.94, -2.39, 0), new Vector(-2.68, 5.72, 0));
        assertNull(triangle.findIntersections(ray),
                "The intersection point is outside of the triangle - 0 intersections expected");

        // TC02: The ray intersects the plane in front of one of the vertex:
        ray = new Ray(new Point(-0.93, 6.2, 0), new Vector(2.54, 2.23, 0));
        assertNull(triangle.findIntersections(ray),
                "The intersection point is outside of the triangle - 0 intersections expected");

        // TC03: The ray intersects the plane within the boundaries of the triangle
        ray = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3));
        assertEquals(1, triangle.findIntersections(ray).size(),
                "The intersection point is inside the triangle - 1 intersections expected");

        // =============== Boundary Values Tests ==================

        // TC11: The ray intersects on one of the sides of the triangle
        ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(-0.15, 3.07, -2.14));
        assertNull(triangle.findIntersections(ray), "The intersection point is on the side - 0 intersections expected");

        // TC12: The ray intersects on one of the vertices of the triangle
        ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(1.3, 0.71, -1.44));
        assertNull(triangle.findIntersections(ray), "The intersection point is on the vertex - 0 intersections expected");

        // TC13: The ray on the straight line continuing the side of the triangle
        ray = new Ray(new Point(3.86, -4.95, 0), new Vector(2.97, 1.28, 0));
        assertNull(triangle.findIntersections(ray),
                "The intersection point is outside of the triangle - 0 intersections expected");

    }
}