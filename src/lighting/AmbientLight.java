package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *The AmbientLight class represents an ambient light source in a 3D scene
 * @author Zili and Ayala
 */
public class AmbientLight {

    private final Color intensity;

    /**
     *no ambient light
     */
    public static AmbientLight NONE=new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an ambient light object with a given intensity and ambient
     * coefficient. The intensity of the light is calculated by scaling the ambient
     * coefficient with the given color
     * @param IA the color of the light source
     * @param KA the ambient coefficient of the light source
     */
    public AmbientLight(Color IA, Double3 KA){ this.intensity = IA.scale(KA); }

    /**
     * Constructs an ambient light object with the given ambient coefficient. The
     * intensity of the light is calculated by scaling the ambient coefficient with
     * the default color BLACK.
     *
     * @param IA the color of the light source
     * @param KA the ambient coefficient of the light source
     */
    public AmbientLight(Color IA, double KA) { this.intensity = IA.scale(KA); }

    /**
     * get intensity of the ambient light.
     * @return the intensity of the ambient light
     */
    public Color getIntensity() { return intensity; }


}