package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The SimpleRayTracer class is a concrete implementation of the RayTracerBase
 * abstract class. This class is responsible for tracing rays in the scene and
 * calculating their color. The class implements the traceRay method, which
 * returns the color of the closest point of intersection with an object in the
 * scene. The class also implements the calcColor method, which calculates the
 * color at a given point in the scene.
 *
 * @author Zili and AYala
 *
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a RayTracerBasic object with the given scene
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {super(scene);}

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at a given point in the scene
     * @param point the point for which to calculate the color.
     * @return the color at the given point in the scene.
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
