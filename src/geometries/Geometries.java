package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Geometries implements Intersectable{
    /**
     *
     */
    private final List<Intersectable> geometricBodies=new LinkedList<Intersectable>();

    /**
     *
     */
    public Geometries(){}

    /**
     *
     * @param geometries
     */
    public Geometries(Intersectable... geometries){
        this();
        add(geometries);
    }

    /**
     *
     * @param geometries
     */
    public void add(Intersectable... geometries){
        geometricBodies.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
