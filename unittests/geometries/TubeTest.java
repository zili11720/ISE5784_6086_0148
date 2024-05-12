package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Ayala
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Tube tube = new Tube(Math.sqrt(2),ray);

        // =============== Equivalence Partitions Tests ==============
        // TC01: simple check
        assertEquals(new Vector(1, 1, 0).normalize(), tube.getNormal(new Point(1, 1, 2)),
                "ERROR: the normal is not correct");

        // =============== Boundary Values Tests ==================
        // TC11: Test for when p-p0 is orthogonal to the ray of the tube
        assertEquals(new Vector(1, 1, 0).normalize(), tube.getNormal(new Point(1, 1, 1)),
                "ERROR: the normal is not correct");
    }
}