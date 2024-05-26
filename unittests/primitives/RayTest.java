package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Zili
 */
class RayTest {

    /**
     * Test for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void tastGetPoint() {

        Ray ray=new Ray(new Point(1,0,0),new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for positive distance t>0
        assertEquals(new Point(3,0,0), ray.getPoint(2), "ERROR: getPoint doesn't work for a positive distance");
        // TC02:Test for negative distance t<0
        assertEquals(new Point(-1,0,0), ray.getPoint(-2), "ERROR: getPoint doesn't work for a negative distance");
        // =============== Boundary Values Tests ==================
        // TC11:Test for 0 distance. t=0
        assertEquals(new Point(1,0,0), ray.getPoint(0),
                "ERROR: getPoint doesn't work for no distance, the head of the ray is the expected output");
    }

    /**
     * Test for {@link primitives.Ray#findClosestPoint(java.util.List)}
     */
    @Test
    void testFindClosestPoint() {
        Vector vector = new Vector(0, -0.5, 0);
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        Point p3 = new Point(3, 3, 3);

        List<Point> list = List.of(p1, p2, p3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: The closest point is in the middle of the list
        Ray ray1 = new Ray(new Point(2, 2.5, 2), vector);
        assertEquals(p2, ray1.findClosestPoint(list), "ERROR: The closest point is in the middle");

        // =============== Boundary Values Tests ==================
        // TC10: The closest point is the first point in the list
        Ray ray2 = new Ray(new Point(1, 1.5, 1), vector);
        assertEquals(p1, ray2.findClosestPoint(list), "ERROR: The closest point is the first one");

        // TC11: The closest point is the last point in the list
        Ray ray3 = new Ray(new Point(3, 3.5, 3), vector);
        assertEquals(p3, ray3.findClosestPoint(list), "ERROR: The closest point is the last one");

        // TC12: The list is null
        list = List.of(); // empties the list
        assertNull(ray3.findClosestPoint(list), "ERROR: The list is empty");
    }
}