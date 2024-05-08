package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 * @author Ayala
 */
class VectorTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;


    @Test
    void testAdd() {
        Vector v1= new Vector(1, 2, 3);
        Vector v2= new Vector(5, 6, 8);
        assertEquals(new Vector(6,8,11),v1.add(v2));

        assertThrows(
                IllegalArgumentException.class,
                ()->v1.add(new Vector(-1,-2,-3)),
                "ERROR: Vector+ -itself does not throw an exception");
    }

    @Test
    void testScale() {
        Vector v1= new Vector(1, 2, 3);
        assertEquals(new Vector(2,4,6),v1.scale(2));

        assertThrows(
                IllegalArgumentException.class,
                ()->v1.scale(0),
                "ERROR: Vector+ -itself does not throw an exception");

    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testLengthSquared() {
        Vector v1= new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(),DELTA,"ERROR:lengthSquared() wrong value");
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
    }
}