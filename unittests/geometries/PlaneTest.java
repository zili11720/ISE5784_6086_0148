package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Ayala
 */
public class PlaneTest {

    /**
     * test for {@link geometries.Plane#Plane(Point, Point, Point)}.
     */
    @Test
    public void testPlane() {
        // =============== Boundary Values Tests ==================
        // TC11: Three points on the same ray
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3),
                        new Point(2, 4, 6),
                        new Point(4, 8, 12)),
                "ERROR: constructed a Plane with three points on the same ray");

        // TC12: Two points united
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3),
                        new Point(1, 2, 3),
                        new Point(1, 1, 1)),
                "ERROR: Constructed a Plane with two identical points");
    }
    /**
     * test for {@link geometries.Plane#getNormal()}
     */
    @Test
    public void testGetNormal() {
        Plane pl = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

        assertEquals(1, pl.getNormal().length(),0.0001, "ERROR: Normal length different than 1");

        // =============== Equivalence Partitions Tests ==============
        // TC01:
        Vector expectedNormal1 = new Vector(1/Math.sqrt(3), 1/Math.sqrt(3), 1/Math.sqrt(3));
        Vector expectedNormal2 = new Vector(-1/Math.sqrt(3), -1/Math.sqrt(3), -1/Math.sqrt(3));
        Vector actualNormal = pl.getNormal(new Point(1, 0, 0));
        assertTrue(actualNormal.equals(expectedNormal1) || actualNormal.equals(expectedNormal2), "ERROR: Wrong normal calculation for a plane");

    }

    /**
     * Test for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        Plane myPlane = new Plane(new Point(0, 5, 0), new Point(-5, 0, 0), new Point(0, 0, 3));

        // ============ Equivalence Partitions Tests ================
        // TC01: The Ray must be neither orthogonal nor parallel to the plane
        // Ray does not intersect the plane
        Ray myRay = new Ray(new Point(1, 2, 0), new Vector(-3, -7, 0));
        assertNull(myPlane.findIntersections(myRay),
                "Ray is neither orthogonal nor parallel but doesnt intersects the plane");

        // TC02:
        // Ray intersects the plane
        myRay = new Ray(new Point(4, 3, 0), new Vector(-5.75, 3.57, 0));
        // plane and intersects the plane
        assertEquals(1, myPlane.findIntersections(myRay).size(),
                "Ray is neither orthogonal nor parallel and intersects the plane ");
        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane:
        // TC11: the ray is included in the plane
        myRay = new Ray(new Point(0, 5, 0), new Vector(-5, 0, 0));
        assertNull(myPlane.findIntersections(myRay), "An included ray supposed to have zero intersection points");
        // TC12: the ray is not included in the plane
        myRay = new Ray(new Point(0, -5, 0), new Vector(5, 0, 0));
        assertNull(myPlane.findIntersections(myRay), "A not included ray supposed to have zero intersection points");

        //**** Group:Ray is orthogonal to the plane:
        // TC13: the ray starts before the plane
        myRay = new Ray(new Point(2, 4, 0), new Vector(-3, 3, 5));
        assertEquals(1, myPlane.findIntersections(myRay).size(),
                "Ray is orthogonal to the plane and starts before the plane- 1 intersection expected");
        // TC14:the ray starts at the plane
        myRay = new Ray(new Point(-5, 0, 0), new Vector(-3, 3, 5));
        assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts at the plane- 0 intersection expected ");
        // TC15:the ray starts after the plane
        myRay = new Ray(new Point(-7, 2, 4), new Vector(-3, 3, 5));
        assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts after the plane- 0 intersection expected");

        //Ray is neither orthogonal nor parallel to the plane and begins at the plane:
        // TC16:
        myRay = new Ray(new Point(-1, -1, 0), new Vector(1, 0, 0));
        assertNull(myPlane.findIntersections(myRay),
                "Ray is neither orthogonal nor parallel to the plane and begins at the plane");

        // Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
        // TC17:
        myRay = new Ray(new Point(0, 0, 3), new Vector(-5, 4, -3));
        assertNull(myPlane.findIntersections(myRay),
                "Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane");


    }
}