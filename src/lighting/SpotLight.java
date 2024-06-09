package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a spotLight source in a lighting system.
 * @author Zili and Ayala
 */
public class SpotLight extends PointLight {

    /**
     * Represents the direction of the spotLight source.
     */
    private Vector direction;

    /**
     * Creates a new SpotLight with the specified intensity, position, and direction.
     *
     * @param intensity the color and intensity of the light
     * @param position  the position of the light source
     * @param direction the direction in which the light is emitted
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    /**
     * Sets the constant attenuation factor of the light.
     * @param kC the constant attenuation factor to set
     * @return the SpotLight object for method chaining
     */
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    /**
     * Sets the linear attenuation factor of the light.
     * @param kL the linear attenuation factor to set
     * @return the spotLight object for method chaining
     */
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkQ(kL);
    }

    /**
     * Sets the quadratic attenuation factor of the light.
     * @param kQ the quadratic attenuation factor to set
     * @return the SpotLight object for method chaining
     */
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }


}