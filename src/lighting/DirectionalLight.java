package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a 3D scene.
 * @author Zili and Ayala
 */
public class DirectionalLight extends Light implements LightSource {

    /** The direction of the light rays in a DirectionalLight object. */
    private Vector direction;

    /**
     * Constructs a directional light object with the given intensity and direction.
     * @param intensity The intensity of the light source.
     * @param direction The direction of the light rays.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point point) {
        return direction;
    }
}
