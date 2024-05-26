package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * pds
 */
public class Scene {

    /** The name of the scene. */
    public final String name;
    /** The background color of the scene. */
    public Color background = Color.BLACK;
    /** The ambient light of the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** The geometries of the scene. */
    public Geometries geometries = new Geometries();

    /**
     *
     * @param name
     */
    public Scene(String name){
        this.name=name;
    }

    /**
     *
     * @param background
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     *
     * @param ambientLight
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     *
     * @param geometries
     * @return
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
