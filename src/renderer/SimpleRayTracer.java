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
 * scene. The color is being calculated considering any elements such as refraction,
 * reflection etc
 *
 * @author Zili and AYala
 *
 */
public class SimpleRayTracer extends RayTracerBase {

    //Recursion stopping conditions for transparency
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    //Stopping conditions of reflection recursion
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INIT_CALC_COLOR_K = Double3.ONE;

    /**
     * Constructs a RayTracerBasic object with the given scene
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {super(scene);}

    // Constant for ray origin offset for shadows
    private static final double DELTA = 0.1;


    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint==null ? scene.background: calcColor(closestPoint,ray);
    }

    @Override
    public Color traceRay(List<Ray> rays)
    {
        if(rays == null)
            return scene.background;
        Color color = scene.background;
        for (Ray ray : rays)
            color = color.add(traceRay(ray));
        color = color.add(scene.ambientLight.getIntensity());
        return color.reduce(rays.size());
    }

    /**
     * Calculates the color of a given point in the scene recursively.
     * @param geoPoint the point to calculate the color for
     * @param ray      the ray that intersects the point
     * @return the color at the given point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INIT_CALC_COLOR_K).add(scene.ambientLight.getIntensity());
    }
    /**
     * Calculates the color at a given point in the scene, taking into account local and global effects.
     * @param geoPoint The geometric point in the scene.
     * @param ray      The ray that intersects the geometric point.
     * @param level    The recursion level for global effects.
     * @param k        The coefficient values for global effects.
     * @return The calculated color at the given point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray,k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    /**
     * Calculates the local effects of color at a point in the scene
     * @param gp  the geometry point to calculate color for
     * @param ray the ray that intersects the point
	 * @return the color at the given point, accounting for local effects
	 */
    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)//הוקטורים מאונכים
            return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color =gp.geometry.getEmission() ;

        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(lightVector));
            if (nl * nv > 0) { // sing(nl) ==sing(nv)
                Double3 ktr=transparency(gp,lightSource,lightVector,n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                            lightIntensity.scale(calcSpecular(material, n, lightVector, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the global effects (reflection and refraction) at a given geometric point.
     * @param gp    The geometric point in the scene.
     * @param ray   The ray that intersects the geometric point.
     * @param level The recursion level for global effects.
     * @param k     The coefficient values for global effects.
     * @return The calculated color due to global effects at the given point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffects(gp, level, material.kR, k, constructReflectedRay(gp, v, n))
                .add(calcGlobalEffects(gp, level, material.kT, k, constructRefractedRay(gp, v, n)));
    }

    /**
     * Calculates the global effects (reflection and refraction) at a given
     * geometric point.
     *
     * @param geoPoint The geometric point in the scene.
     * @param level    The recursion level for global effects.
     * @param kx       The coefficient values for the specific effect (reflection or refraction).
     * @param k        The overall coefficient values for global effects.
     * @param ray      The ray used for the specific effect (reflection or refraction).
     * @return The updated color after considering the global effects.
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, int level,  Double3 kx, Double3 k, Ray ray) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint reflectedPoint = findClosestIntersection(ray);
        if (reflectedPoint == null)
            return scene.background.scale(kx);
        return calcColor(reflectedPoint, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Finds the closest point of intersection between the given ray and objects in the scene.
     * @param ray the ray to be tested for intersections.
     * @return the closest GeoPoint of intersection, or null if there are no intersections.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        //let ray calculate the distance
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
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
     * Constructs a reflected ray at a given geometric point.
     * @param gp The geometric point in the scene.
     * @param v  The incident ray direction.
     * @param n  The normal vector at the point.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        //starting point of the ray moves on the geometie's normal towards the new ray
        Vector reflectedVector = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(gp.point, reflectedVector, n);
    }

    /**
     * Constructs a refracted ray at a given geometric point.
     * @param gp The geometric point in the scene.
     * @param v  The incident ray direction.
     * @param n  The normal vector at the point.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        //starting point of the ray moves on the geometrie's normal towards the new ray
        return new Ray(gp.point, v, n);
    }


    /**
     * function will check if point is unshaded
     * @param gp geometry point to check
     * @param l  light vector
     * @param n  normal vector
     * @return true if unshaded
     */
    @SuppressWarnings("unused")
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        //add delta in ctr to prevent the head of the ray to count as a shadow
        // and to prevent mistakes du to small miscalculations (mottled affect)
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        //Point point = gp.point.add(n.scale(Util.alignZero(n.dotProduct(lightDirection)) < 0 ? DELTA : -DELTA));
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return true;
        double distance = lightSource.getDistance(gp.point);//distance from light source to the point
        for (GeoPoint intersection : intersections) {
            //make sure the object is between the light source and the point
            if (alignZero(intersection.point.distance(gp.point)) <= distance
                    //only objects with kt=0 will cast a shade
                   && intersection.geometry.getMaterial().kT.equals(Double3.ZERO))
                return false;
        }
        return true;
    }
    /**
     * Calculates the transparency factor for a given geometric point and light source.
     * @param geoPoint    The geometric point in the scene.
     * @param l     The direction from the light source to the point.
     * @param n     The normal vector at the point.
     * @param ls The light source.
     * @return The transparency factor (ktr) as a Double3 vector.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);//use ctr that adds delta
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        double lightDistance = ls.getDistance(geoPoint.point);
        for (GeoPoint gp1 : intersections) {
            if (alignZero(gp1.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp1.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }
}
