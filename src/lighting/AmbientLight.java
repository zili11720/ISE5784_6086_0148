package lighting;

import primitives.Color;
import primitives.Double3;

/**
 *
 */
public class AmbientLight {
    /**
     *
     */
    private final Color intensity;
    /**
     *
     */
    public static AmbientLight NONE=new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     *
     * @param IA
     * @param KA
     */
    AmbientLight(Color IA, Double3 KA){ this.intensity = IA.scale(KA); }

    /**
     *
     * @param IA
     * @param KA
     */
    public AmbientLight(Color IA, double KA) { this.intensity = IA.scale(KA); }

    /**
     *
     * @return
     */
    public Color getIntensity() { return intensity; }


}