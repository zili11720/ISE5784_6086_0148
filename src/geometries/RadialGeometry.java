package geometries;

/**
 *
 */
public abstract class RadialGeometry implements Geometry {
    /** The radius of the geometry */
    protected double radius;

    /**
     * Constructs the geometry with the given radius
     * @param radius the radius of the geometry
     */
    public RadialGeometry(double radius) {this.radius = radius;}
}
