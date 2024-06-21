package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

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

    // Constant for ray origin offset
    private static final double DELTA = 0.1;


    @Override
    public Color traceRay(Ray ray) {
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);
    }

    /**
     * Calculates the color at a given GeoPoint in the scene
     * @param geoPoint the point for which to calculate the color.
     * @return the color at the given point in the scene.
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        return scene.ambientLight.getIntensity() .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the local effects of color at a point in the scene
     * @param gp  the geometry point to calculate color for
     * @param ray the ray that intersects the point
	 * @return the color at the given point, accounting for local effects
	 */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color =gp.geometry.getEmission() ;

        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(lightVector));
            if ((nl * nv > 0) && unshaded(gp, lightVector, n, lightSource, nv) ){ // sing(nl) ==sing(nv)
                Color lightIntensity = lightSource.getIntensity(gp.point);
                color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                        lightIntensity.scale(calcSpecular(material, n, lightVector, nl, v)));
            }
        }
        return color;
    }

    /**
     * Calculates the specular color at a point on a geometry
     * @param material    the material of the geometry
     * @param normal      the normal of the geometry
     * @param lightVector the light vector
     * @param nl          the dot product of the normal and light vector
     * @param vector      the direction of the ray
     * @return the specular color at the given point
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
        return material.kS.scale(Math.pow(max, material.nShininess));

    }

    /**
     * Calculates the diffusive color at a point on a geometry
     * @param material the material of the geometry
     * @param nl       the dot product of the normal and light vector
     * @return the diffusive color at the given point
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }



    /**
     * This method checks if a given point on an object is unshaded (not in shadow) by casting a ray
     * from the point towards the light source and checking for intersections with other objects in the scene.
     *
     * @param gp The geometric point on the object to check.
     * @param l The direction vector from light source towards the point.
     * @param n The normal vector at the point on the object's surface.
     * @param lightSource The light source in the scene.
     * @param nv The dot product of the normal vector and the view direction (used to determine the direction of the offset).
     * @return True if the point is unshaded, false otherwise.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nv) {
        // Calculate the light direction vector (from the point to the light source)
        Vector lightDirection = l.scale(-1); // from point to light source

        // Calculate a small offset to move the point slightly along the normal vector to avoid shadow acne
        Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);

        // Move the point slightly along the normal vector to create the offset point
        Point point = gp.point.add(epsVector);

        // Create a ray from the offset point towards the light source
        Ray lightRay = new Ray(point, lightDirection);

        // Find intersections of the light ray with objects in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        // If there are intersections, check if any are closer to the point than the light source
        if (intersections != null) {
            // Calculate the distance from the point to the light source
            double distance = lightSource.getDistance(gp.point);

            // Loop through the intersections
            for (GeoPoint intersection : intersections) {
                // If an intersection is closer than the light source, the point is shaded
                if (intersection.point.distance(gp.point) < distance) {
                    return false; // Point is in shadow
                }
            }
        }
        // If no intersections are closer than the light source, the point is unshaded
        return true;
    }

}
