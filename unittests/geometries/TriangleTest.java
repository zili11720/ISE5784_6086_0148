package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 * @author Ayala
 */
public class TriangleTest {

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
//        Triangle t = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
//        double sqrt = Math.sqrt(1d / 3);
//        assertEquals(new Vector(sqrt, sqrt, sqrt), t.getNormal(new Point(0, 0, 1)), "ERROR: Wrong normal");

//        try {
//            new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
//        } catch (IllegalArgumentException e) {
//            fail("Failed constructing a correct triangle");
//        }

        // =============== Equivalence Partitions Tests ==============
        // TC01: simple check
        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
        assertEquals(new Vector(0, 0, 1),
                triangle.getNormal(new Point(0, 1, 0)),
                "ERROR: Wrong normal calculation for a triangle");
    }
}