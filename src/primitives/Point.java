package primitives;

/**
 * The class represents a point in 3-dimensional space
 * @author Zili
 */
public class Point {
    /**
     * 3-dimensional coordinates of this point.
     */
    protected final Double3 xyz;

    /**
     * Constructs a new point according to 3 doubles
     * @param x first coordinate of the point
     * @param y second coordinate of the point
     * @param z third coordinate of the point
     */
    public Point(double x, double y, double z) {xyz=new Double3(x,y,z);}

    /**
     * Constructs a new point according to another point
     * @param xyz a 3-dimensional point
     */
    Point(Double3 xyz) {this.xyz=xyz;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
            && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {return " "+xyz;}

    /**
     * Returns a new vector from a given point to the current point by subtracting the two points
     * @param other a point that will be the head of the new vector
     * @return a new vector from a given point to the current point
     */
    public Vector subtract(Point other) {return new Vector(xyz.subtract(other.xyz));}

    /**
     * Returns a new point after adding the given vector to the current point
     * @param vec a vector to add to the current point
     * @return a new point after adding the given vector to the current point
     */
    public Point add(Vector vec) {return new Point(xyz.add(vec.xyz));}

    /**
     * Returns the squared distance between the current point to a given point
     * @param p the point to calculate the distance to
     * @return the squared distance between the current point to a given point
     */
    public double distanceSquared(Point p) {
        return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2)
                + (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);
    }

    /**
     * Returns the distance between the current point to a given point
     * @param p the point to calculate the distance to
     * @return the distance between the current point to a given point
     */
    public double distance(Point p) {return Math.sqrt(distanceSquared(p));}
}
