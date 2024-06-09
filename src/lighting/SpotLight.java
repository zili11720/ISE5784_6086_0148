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
     * Represents the narrowness of the light beam emitted by the spot light source.
     * A value of 1 represents a wide beam, while values less than 1 create a
     * narrower beam.
     */
    private double narrowBeam = 1;

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

    /**
     * Sets the narrowness of the light beam. A value of 1 represents a wide beam,
     * while values less than 1 create a narrower beam.
     *
     * @param narrowBeam the narrowness of the light beam
     * @return the SpotLight object for method chaining
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Calculates and returns the intensity of the light at the specified point. The
     * intensity is attenuated based on the distance and the angle between the light
     * direction and the surface normal.
     *
     * @param point the point at which to calculate the intensity
     * @return the attenuated intensity at the point
     */
    @Override
    public Color getIntensity(Point point) {
        // check if it is flashlight
        return narrowBeam != 1
                ? super.getIntensity(point).scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam))
                : super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));

    }


}