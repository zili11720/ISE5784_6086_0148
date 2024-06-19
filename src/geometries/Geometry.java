package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 *An abstract class for a 3D Geometry
 *@author Zili
 */
public abstract class Geometry extends  Intersectable{

    /**
     * self emission of a geometric object
     * Initialized to black
     */
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Returns normalized vector to a geometry at a given point
     * @param point the point where the normal is required
     * @return  normalized vector to a geometry at a given point
     */
     public abstract Vector getNormal(Point point);


    /**
     * Returns the emission color of the geometry.
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Returns the material of the geometry.
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission the emission color to set
     * @return the geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Sets the material of the geometry.
     * @param material the material to set
     * @return the geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

}
