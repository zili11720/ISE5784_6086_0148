package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *A class for a group of different geometries using the composite design pattern
 * @author Zili
 */
public class Geometries implements Intersectable{

    /**
     *A list of different geometric objects in the scene
     */
    private final List<Intersectable> geometricBodies=new LinkedList<Intersectable>();

    /**
     *Empty constructor
     */
    public Geometries(){}

    /**
     *Parameters constructor
     * @param geometries Different geometric bodies to add to the list
     */
    public Geometries(Intersectable... geometries){
        this();
        add(geometries);
    }

    /**
     *The method adds all the geometric bodies in 'geometries' to the list structure
     * @param geometries Different geometric bodies to add to the list
     */
    public void add(Intersectable... geometries){
        geometricBodies.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        //Find the intersections for every shape
        for (Intersectable geometry : this.geometricBodies) {
            List<Point> tmpIntersections = geometry.findIntersections(ray);
            if (tmpIntersections != null) {
                if (intersections == null) {//for the first intersection found
                    intersections = new LinkedList<Point>();
                }
                intersections.addAll(tmpIntersections);
            }
        }
        return intersections;
    }
}
