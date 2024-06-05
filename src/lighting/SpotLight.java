package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a spotLight source in a lighting system.
 * @author Zili and Ayala
 */
public class SpotLight extends PointLight{

    /**
     * Represents the direction of the spotLight source.
     */
    private Vector direction;

    /**
     * Creates a new SpotLight with the specified intensity, position, and direction.
     * @param intensity the color and intensity of the light
     * @param position  the position of the light source
     * @param direction the direction in which the light is emitted
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    /////////////////// set to double/////////////////////
}
