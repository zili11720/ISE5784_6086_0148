package geometries;

/**
 *Class RadialGeometry represents a 3D geometry that has a radius
 * @author Ayala
 */
public abstract class RadialGeometry implements Geometry {

    /** The radius of the geometry */
    protected final double radius;

    /**
     * Constructs a radial geometry with the given radius
     * @param radius the radius of the geometry
     */
    RadialGeometry(double radius) {this.radius = radius;}
}
