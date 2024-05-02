package primitives;

/**
 * Represents a vector that starts from the origin to a specific point
 *  * @author zili
 */
public class Vector extends Point{
    /**
     * Constructs a new vector according to three doubles
     * @param x first coordinate of the end point of the vector
     * @param y second coordinate of the end point of the vector
     * @param z third coordinate of the end point of the vector
     *@throws IllegalArgumentException in case of a zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("A vector must not be the zero vector");
    }
    /**
     * Constructs a new vector according to a Double3 point
     * @param xyz a double3 point for the tail of the vector
     * @throws IllegalArgumentException in case of a zero vector
     */
    Vector(Double3 xyz){
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("A vector must not be the zero vector");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override
    public String toString() {
        return "->"+super.toString();
    }

    /**
     * Adds this vector to the given vector and returns the result as a new vector.
     * @param v the vector to add to this vector
     * @return the sum of this vector and the given vector as a new vector
     */
    public Vector add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }

    /**
     * Scales the vector by a given scalar and returns the result as a new vector
     * @param scalar the scalar to scale the vector by.
     * @return the current vector scaled by the given scalar as a new vector
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculates and returns the dot product between this vector to a given vector
     * @param vec the other vector to compute the dot product with
     * @return the dot product between this vector to a given vector
     */
    public double dotProduct(Vector vec) {
        return this.xyz.d1*vec.xyz.d1+this.xyz.d2*vec.xyz.d2+this.xyz.d3*vec.xyz.d3;
    }

    /**
     * Calculates and returns the cross product between this vector to a given vector
     * @param vec the other vector to compute the cross product with
     * @return the cross product between this vector to a given vector as a new vector
     */
    public Vector crossProduct(Vector vec) {
        return new Vector((this.xyz.d2 * vec.xyz.d3) - (this.xyz.d3 * vec.xyz.d2), (this.xyz.d3 * vec.xyz.d1) - (this.xyz.d1 * vec.xyz.d3),
                (this.xyz.d1 * vec.xyz.d2) - (this.xyz.d2 * vec.xyz.d1));
    }

    /**
     * Calculates the squared length of the vector
     * @return the squared length of the vector
     */
    public double lengthSquared(){return dotProduct(this);}

    /**
     * Calculates the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector by dividing the vector by its length
     * @return the normalized vector as a new vector
     */
    public Vector normalize() {return new Vector(xyz.reduce(length()));}



}
