package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 *An abstract class for a 3D Geometry
 *@author Zili
 */
public abstract class Geometry extends  Intersectable{

    protected Color emission = Color.BLACK;
    /**
     * Returns normalized vector to a geometry at a given point
     * @param point the point where the normal is required
     * @return  normalized vector to a geometry at a given point
     */
     public abstract Vector getNormal(Point point);

    /**
     * Returns the emission color of the geometry.
     *
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
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

}
