package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Zili
 */
public class PointTest {

    Point  p0         = new Point(0,0,0);
    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);

    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TA01: Test for subtracting points
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        // TA11: Test for subtracting a point from itself
        assertThrows(IllegalArgumentException.class,()->p1.subtract(p1),
                "ERROR: (point - itself) does not throw an exception" );
        //TA12: Test for subtracting zero point
        assertEquals(v1, p1.subtract(p0),"ERROR:  (point2 -zero point) does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TA01: Test for adding a vector to a point
        assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // TA11: Test that the adding of zero point is correct
        assertEquals(p1, p0.add(v1),"ERROR: Function add doesnt work correctly for zero point");
        // TA12: Test that the adding of opposites is correct
        assertEquals(p0, p1.add(v1Opposite),"ERROR: (point + vector) = center of coordinates does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    public void testDistanceSquared() {
        // =======Equivalence Partitions Tests=======
        // TD01: Test that the distance between two points is calculated correctly
        assertEquals(9, p1.distanceSquared(p3), "ERROR: squared distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TA11:Test point squared distance to itself
        assertEquals(0, p1.distanceSquared(p1),"ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    public void distance() {
        // =======Equivalence Partitions Tests=======
        // TC01: Testing if the squared distance between two points is calculated correctly
        assertEquals(3, p1.distance(p3) , "ERROR: distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TA11:Test point distance to itself
        assertEquals(0, p1.distanceSquared(p1),"ERROR: point distance to itself is not zero");
    }
}