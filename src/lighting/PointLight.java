package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    /** Represents the position of the point light source. */
   protected Point position;
    /** Represents the constant attenuation factor of the point light source. */
    private double kC = 1;
    /** Represents the linear attenuation factor of the point light source. */
    private double kL = 0;
    /** Represents the quadratic attenuation factor of the point light source. */
    private double kQ = 0;

    /**
     * Creates a new PointLight with the specified intensity and position.
     * @param intensity the color and intensity of the light
     * @param position  the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the light.
     * @param kC the constant attenuation factor to set
     * @return the PointLight object for method chaining
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor of the light.
     * @param kL the linear attenuation factor to set
     * @return the PointLight object for method chaining
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    /**
     * Sets the quadratic attenuation factor of the light.
     * @param kQ the quadratic attenuation factor to set
     * @return the PointLight object for method chaining
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;

    }


    @Override
    public Color getIntensity(Point point) {
        double d = position.distance(point);
        return super.getIntensity().scale(1d/(kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point point) { return point.subtract(position).normalize();
    }
}
