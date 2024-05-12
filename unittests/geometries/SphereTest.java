package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Ayala
 */
public class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        double radius = 5;
        Point center = new Point(1, 2, 3);
        Sphere sphere = new Sphere(radius,center);
        Vector normal = new Vector(4d/5, 0, 3d/5);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(normal, sphere.getNormal(new Point(5, 2, 6)), "ERROR: Wrong normal calculation for a sphere");
    }
}