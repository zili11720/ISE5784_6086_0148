package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GeometriesTest {
    /**
     * Test method for
     * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersections() {
        Geometries geometries = new Geometries(new Sphere(0.5,new Point(0, 0, 2)),
                new Polygon(new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 0, 0), new Point(0, -1, 0)),
                new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: A few geometries intersects
        result = geometries.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(2, 2, 2)));
        assertEquals(2, result.size(), "A few geometries intersects");

        // =============== Boundary Values Tests ==================
        // TC11: All geometries intersects
        result = geometries.findIntersections(new Ray(new Point(0.2, 0.2, -0.6), new Vector(0, 0, 1)));
        assertEquals(4, result.size(), "All geometries intersects");

        // TC12: Only 1 geometry intersect
        result = geometries.findIntersections(new Ray(new Point(0.2, 0.2, 0.2), new Vector(1, 1, 1)));
        assertEquals(1, result.size(), "Only 1 geometry intersect");

        // TC13: No geometries intersects
        assertNull(geometries.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "No geometries intersects");

        // TC14: Empty list of geometries
        assertNull(new Geometries().findIntersections(new Ray(new Point(1, 2, 3), new Vector(2, 2, 2))),
                "Empty list of geometries");

    }
}
