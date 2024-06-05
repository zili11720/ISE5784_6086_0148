package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *The AmbientLight class represents an ambient light source in a 3D scene
 * @author Zili and Ayala
 */
public class AmbientLight extends Light {

    /**
     *no ambient light
     */
    public static AmbientLight NONE=new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an ambient light object with a given intensity and ambient
     * coefficient using the super constructor.
     * The intensity of the light is calculated by scaling the ambient coefficient with the given color
     * @param IA the color of the light source
     * @param KA the ambient coefficient of the light source
     */
    public AmbientLight(Color IA, Double3 KA){ super(IA.scale(KA)); }

    /**
     * Constructs an ambient light object with the given ambient coefficient using the super constructor.
     * The intensity of the light is calculated by scaling the ambient coefficient with
     * the default color BLACK.
     *
     * @param IA the color of the light source
     * @param KA the ambient coefficient of the light source
     */
    public AmbientLight(Color IA, double KA) { super(IA.scale(KA)); }

}