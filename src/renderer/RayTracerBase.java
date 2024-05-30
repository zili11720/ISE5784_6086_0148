package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class represents a base class for ray-tracing algorithms in
 * a 3D scene.
 *
 * @author Zili
 */
public abstract class RayTracerBase {

    protected final Scene scene;

    /**
     * Constructs a RayTracerBase object with the given scene
     * @param scene the scene to trace rays in
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a given ray and returns the color of the first hit object in the
     * scene, or black if there is no hit.
     * @param ray the ray to trace
     * @return the color of the first hit object in the scene, or black if there is no hit
     */
    public abstract Color traceRay(Ray ray);
}
