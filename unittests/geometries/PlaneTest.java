package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
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
        // =============== Equivalence Partitions Tests ==============
//        // TC01: constructor works well
//        try {
//            new Plane(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
//        } catch (IllegalArgumentException e) {
//            fail("Failed constructing a correct plane");
//        }

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

        assertEquals(1, pl.getNormal().length(), "ERROR: Normal length different than 1");

        // =============== Equivalence Partitions Tests ==============
        // TC01:
        assertEquals(new Vector(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),
                pl.getNormal(new Point(1, 0, 0)),
                "ERROR: Wrong normal calculation for a plane");
    }
}