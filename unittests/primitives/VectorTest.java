package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Zili
 */
class VectorTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(2, 4, 6);
    Vector v3 = new Vector(1, 2, 4);
    Vector v4 = new Vector(1, 4, -3);
    Vector v5 = new Vector(-1, -2, -3);
    Vector v6 = new Vector(3, 6, 9);
    Vector v7 = new Vector(2, 6, 0);
    Vector v8 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TA01: Test that the adding of positive vectors is correct
        assertEquals(v6, v1.add(v2), "Function add doesnt work correctly");
        // TA02: Test that the adding of positive and negative vectors is correct
        assertEquals(v7, v1.add(v4), "Function add doesnt work correctly");
        // =============== Boundary Values Tests ==================
        // TA11: Test that the adding of opposite vectors throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(v5),
                "Function add doesnt work correctךט for adding of opposite vectors");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TS01: Tests that multiplication by a positive scalar is correct
        assertEquals(v2, v1.scale(2),
                "Function scale doesnt work correctly for a positive scalar");
        // TS02: Tests that multiplication by a negative scalar is correct
        assertEquals(v5, v1.scale(-1),
                "Function scale doesnt work correctly for a negative scalar");
        // =============== Boundary Values Tests ==================
        // scalar: 0
        // TS11: Tests that multiplication by zero scalar throw exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "Function scale doesnt work correctly for a negative scalar");

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TD01: Test that dot-product is proper for vectors in the same direction
        assertEquals(28, v1.dotProduct(v2), DELTA,
                "Function dotProduct doesnt work correctly for vectors in the same direction");
        // TD02: Test that dot-product is proper for sharp angle
        assertEquals(17, v1.dotProduct(v3), DELTA,
                "Function dotProduct doesnt work correctly for sharp angle");
        // TD03: Test that dot-product is proper for Orthogonal angle
        assertEquals(0, v1.dotProduct(v4), DELTA,
                "Function dotProduct doesnt work correctly for Orthogonal angle");
        // TD04: Test that dot-product is proper for Obtuse angle
        assertEquals(-3, v3.dotProduct(v4), DELTA,
                "Function dotProduct doesnt work correctly for Obtuse angle");
        // TD05: Test that dot-product is proper for Inverted vectors
        assertEquals(-14, v1.dotProduct(v5), DELTA,
                "Function dotProduct doesnt work correctly for Inverted vectors");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v8);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v8.length(), vr.length(), DELTA,
                "Function crossProduct wrong length result");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),
                "Function crossProduct result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)),
                "Function crossProduct result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v5),
                "Function crossProduct for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TL01: Test length Squared result for positive coordinate
        assertEquals(14, v1.lengthSquared(),
                "Function lengthSquared returns wrong value for positive coordinate");
        // TL02: Test length Squared result for negative coordinate
        assertEquals(14, v5.lengthSquared(),
                "Function lengthSquared returns wrong value for negative coordinate");
        // TL03: Test length Squared result for positive and negative coordinate
        assertEquals(26, v4.lengthSquared(),
                "Function lengthSquared returns wrong value for positive and negative coordinate");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TL01: Test length result for positive coordinate
        assertEquals(Math.sqrt(14), v1.length(),
                "Function Length returns wrong value for positive coordinate");
        // TL02: Test length result for negative coordinate
        assertEquals(Math.sqrt(14), v5.length(),
                "Function Length returns wrong value for negative coordinate");
        // TL03: Test length result for positive and negative coordinate
        assertEquals(Math.sqrt(26), v4.length(),
                "Function Length returns wrong value for negative coordinate");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TN01: Test normalize result for positive coordinate
        assertEquals(new Vector(1 / Math.sqrt(14), 2 / Math.sqrt(14), 3 / Math.sqrt(14)),
                v1.normalize(), "Function normalize test is failed for positive coordinate");
        // TN02: Test normalize result for negative coordinate
        assertEquals(new Vector(-1 / Math.sqrt(14), -2 / Math.sqrt(14), -3 / Math.sqrt(14)),
                v5.normalize(), "Function normalize test is failed for negative coordinate");
        // TN03: Test normalize result for positive and negative coordinate
        assertEquals(new Vector(1 / Math.sqrt(26), 4 / Math.sqrt(26), -3 / Math.sqrt(26)),
                v4.normalize(), "Function normalize test is failed for negative coordinate");
        // TN04: Test normalize result is 1
        assertEquals(1, v1.normalize().length(), "Function normalize result is not 1");

        // =============== Boundary Values Tests ==================
        // TN11: Test vector multiplied by its normalized throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1.normalize()),
                "the normalized vector is not parallel to the original one");
        // TN12: Test vector scalar multiplies its normalized in the same direction
        assertTrue(v1.dotProduct(v1.normalize())>0,
                "the normalized vector is opposite to the original one");
    }
}