package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Zili
 */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        Ray ray = new Ray(Point.ZERO, new Vector(0, 0, 1));
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


    //BONUS
    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Tube tube1 = new Tube(1d,new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
        Vector vAxis = new Vector(0, 0, 1);
        Tube tube2 = new Tube(1d,new Ray(new Point(1, 1, 1), vAxis));
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        ray = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        assertNull(tube1.findIntersections(ray), "ERROR: 0 intersections expected");

        // TC02: Ray's crosses the tube (2 points)
        ray = new Ray(Point.ZERO, new Vector(2, 1, 1));
        List<Point> result = tube2.findIntersections(ray);

        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR: 2 intersections expected");

        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 0.2), new Point(2, 1, 1)), result, "ERROR: Wrong intersections");

        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray = new Ray(new Point(1, 0.5, 0.5), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);

        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 1)), result, "ERROR: Wrong intersections");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line is parallel to the axis (0 points)
        // TC11: Ray is inside the tube (0 points)
        ray = new Ray(new Point(0.5, 0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC12: Ray is outside the tube
        ray = new Ray(new Point(0.5, -0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC13: Ray is at the tube surface
        ray = new Ray(new Point(2, 1, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC14: Ray is inside the tube and starts against axis head
        ray = new Ray(new Point(0.5, 0.5, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC15: Ray is outside the tube and starts against axis head
        ray = new Ray(new Point(0.5, -0.5, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC16: Ray is at the tube surface and starts against axis head
        ray = new Ray(new Point(2, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC17: Ray is inside the tube and starts at axis head
        ray = new Ray(new Point(1, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // begins against axis head
        Point p0 = new Point(0, 2, 1);
        // TC18: Ray's line is outside the tube
        ray = new Ray(p0, new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: Wrong intersections");
        // TC19: Ray's line crosses the tube and begins before
        ray = new Ray(p0, new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR: 2 intersections expected");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(2, 1, 2), new Point(0.4, 1.8, 1.2)), result, "ERROR; wrong intersections");
        // TC20: Ray's line crosses the tube and begins at surface and goes inside
        ray = new Ray(new Point(0.4, 1.8, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR:intersections expected");
        assertEquals(1, result.size(), "ERROR:1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 1.8)), result, "ERROR: wrong intersections");
        // TC21: Ray's line crosses the tube and begins inside
        ray = new Ray(new Point(1, 1.5, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR:intersections expected");
        assertEquals(1, result.size(), "ERROR:1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 1.5)), result, "ERROR: wrong intersections");
        // TC22: Ray's line crosses the tube and begins at the axis head
        ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR:intersections expected");
        assertEquals(1, result.size(), "ERROR:1 intersection expected");
        assertEquals(List.of(new Point(1, 2, 2)), result, "ERROR: wrong intersections");
        // **** Group: Ray is orthogonal but does not begin against the axis head
        // TC23: Ray starts outside and the line is outside (0 points)
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC24: The line is tangent and the ray starts before the tube (0 points)
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC25: The line is tangent and the ray starts at the tube (0 points)
        ray = new Ray(new Point(1, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC26: The line is tangent and the ray starts after the tube (0 points)
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: 0 intersections expected");
        // TC27: Ray starts before (2 points)
        ray = new Ray(new Point(0, 0, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR: 2 intersections expected");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));}
        assertEquals(List.of(new Point(0.4, 0.2, 2), new Point(2, 1, 2)), result, "ERROR: wrong intersections");
        // TC28: Ray starts at the surface and goes inside (1 point)
        ray = new Ray(new Point(0.4, 0.2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR:1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 2)), result, "ERROR: wrong intersections");
        // TC29: Ray starts inside (1 point)
        ray = new Ray(new Point(1, 0.5, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR:1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 2)), result, "ERROR: wrong intersections");
        // TC30: Ray starts at the surface and goes outside (0 points)
        ray = new Ray(new Point(2, 1, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
        // TC31: Ray starts after
        ray = new Ray(new Point(4, 2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
        // TC32: Ray starts before and crosses the axis (2 points)
        ray = new Ray(new Point(1, -1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR:2 intersections expected");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(1, 0, 2), new Point(1, 2, 2)), result, "ERROR: wrong intersections");
        // TC33: Ray starts at the surface and goes inside and crosses the axis
        ray = new Ray(new Point(1, 0, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(1, 2, 2)), result, "ERROR: wrong intersections");
        // TC34: Ray starts inside and the line crosses the axis (1 point)
        ray = new Ray(new Point(1, 0.5, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(1, 2, 2)), result, "ERROR: wrong intersections");

        // **** Group: Ray is orthogonal to axis and begins against the axis head
        // TC35: Ray starts outside and the line is outside (
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: no intersections expected");
        // TC36: The line is tangent and the ray starts before the tube
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: no intersections expected");
        // TC37: The line is tangent and the ray starts at the tube
        ray = new Ray(new Point(1, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: no intersections expected");
        // TC38: The line is tangent and the ray starts after the tube
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "ERROR: no intersections expected");
        // TC39: Ray starts before
        ray = new Ray(new Point(0, 0, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR:2 intersections expected");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 1), new Point(2, 1, 1)), result, "ERROR: wrong intersections");
        // TC40: Ray starts at the surface and goes inside
        ray = new Ray(new Point(0.4, 0.2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 1)), result, "ERROR: wrong intersections");
        // TC41: Ray starts inside
        ray = new Ray(new Point(1, 0.5, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(2, 1, 1)), result, "ERROR: wrong intersections");
        // TC42: Ray starts at the surface and goes outside
        ray = new Ray(new Point(2, 1, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
        // TC43: Ray starts after
        ray = new Ray(new Point(4, 2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
        // TC44: Ray starts before and goes through the axis head
        ray = new Ray(new Point(1, -1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(2, result.size(), "ERROR:2 intersections expected");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(1, 0, 1), new Point(1, 2, 1)), result, "ERROR: wrong intersections");
        // TC45: Ray starts at the surface and goes inside and goes through the axis
        // head
        ray = new Ray(new Point(1, 0, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(1, 2, 1)), result, "ERROR: wrong intersections");
        // TC46: Ray starts inside and the line goes through the axis head
        ray = new Ray(new Point(1, 0.5, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "ERROR: intersections expected");
        assertEquals(1, result.size(), "ERROR: 1 intersection expected");
        assertEquals(List.of(new Point(1, 2, 1)), result, "ERROR: wrong intersections");
        // TC47: Ray starts at the surface and the line goes outside and goes through
        // the axis head
        ray = new Ray(new Point(1, 2, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
        // TC48: Ray starts after and the line goes through the axis head
        ray = new Ray(new Point(1, 3, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "ERROR: wrong intersections");
    }
}