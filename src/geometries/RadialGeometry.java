package geometries;

/**
 *Represents a 3D geometry that has a radius
 * @author ayala
 */
public abstract class RadialGeometry implements Geometry {

    /** The radius of the geometry */
    protected double radius;

    /**
     * Constructs a radial geometry with the given radius
     * @param radius the radius of the geometry
     */
    RadialGeometry(double radius) {this.radius = radius;}
}
