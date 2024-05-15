package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Zili
 */
public class VectorTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    private final Vector v1         = new Vector(1, 2, 3);
    private final Vector v1Opposite = new Vector(-1, -2, -3);
    private final Vector v2         = new Vector(-2, -4, -6);
    private final Vector v3         = new Vector(0, 3, -2);
    private final Vector v4         = new Vector(1, 2, 2);


    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TA01: Test that the adding of vectors is correct
        assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // TA11: Test that the adding of opposite vectors throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
                "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TS01: Tests that multiplication by a positive scalar is correct
        assertEquals(new Vector(2,4,6), v1.scale(2),
                "ERROR: scale doesnt work correctly for a positive scalar");
        // TS02: Tests that multiplication by a negative scalar is correct
        assertEquals(v2, v1.scale(-2),
                "ERROR scale doesnt work correctly for a negative scalar");
        // =============== Boundary Values Tests ==================
        // scalar: 0
        // TS11: Tests that multiplication by zero scalar throw exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "ERROR: function scale does not throw an exception for scalar 0");

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //tD01: Test simple dot product between vectors
        assertEquals(-28, v1.dotProduct(v2), DELTA, "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        // TCD11: dot product between orthogonal vectors
        assertEquals(0,v1.dotProduct(v3),DELTA
               , "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), DELTA,
                "ERROR: crossProduct() wrong length result");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertTrue(isZero(vr.dotProduct(v3)),
                "ERROR: crossProduct() result is not orthogonal to its operands");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TL01: Test length Squared result for a vector
        assertEquals(9, v4.lengthSquared(),DELTA,
                "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TL01: Test length result for positive coordinate
        assertEquals(3, v4.length(),DELTA,
                "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector u = v1.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TN01: Test normalize result for a vector
        assertEquals(new Vector(1 / Math.sqrt(14), 2 / Math.sqrt(14), 3 / Math.sqrt(14)),
                u, "ERROR: Function normalize() returns wrong value");
        //TN02:test the length of a normalized vector
        assertEquals(1,u.length(),DELTA,"ERROR: the normalized vector is not a unit vector");
        // =============== Boundary Values Tests ==================
        // TN11: Test vector multiplied by its normalized throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        // TN12: Test that a vector and its normalized vector are in the same direction
        assertTrue(v1.dotProduct(u)>0,
                "ERROR: the normalized vector is opposite to the original one");
    }
}