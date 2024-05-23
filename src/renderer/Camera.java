package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A class to represent a camera in a 3D scene
 * This class uses the Builder design pattern for constructing a camera object
 * The class implements the Cloneable interface (Marker interface)
 * @author Zili
 */
public class Camera implements Cloneable {

    private Point location=null; // Camera location
    private Vector vTo=null; // Forward direction vector
    private Vector vUp=null; // Up direction vector
    private Vector vRight=null; // Right direction vector
    private double width = 0;
    private double height = 0;
    private double distance = 0;

    /**
     * Default constructor
     */
    private Camera() {
    }


    /**
     * Static method to get a new Builder object for constructing a Camera
     *
     * @return a new Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray from the camera through pixel i,j
     *
     * @param nX amount of columns (row width)
     * @param nY amount of rows (column height)
     * @param j  pixel index for column
     * @param i  pixel index for the row
     * @return the constructed ray - from p0 through the wanted pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = location.add(vTo.scale(distance));     // center of the view plane
        double Ry = height/nY;                      // Ratio - pixel height
        double Rx = width/nX;                       // Ratio - pixel width

        double yJ = alignZero(-(i - (nY - 1) / 2d) * Ry);       // move pc Yi pixels
        double xJ = alignZero((j - (nX - 1) / 2d) * Rx);        // move pc Xj pixels

        Point PIJ = pc;
        if(!isZero(xJ))  PIJ = PIJ.add(vRight.scale(xJ));
        if(!isZero(yJ))  PIJ = PIJ.add(vUp.scale(yJ));

        return new Ray(location, PIJ.subtract(location));
    }

    /**
     * Internal class camera implements Builder design pattern
     */
    public static class Builder {

        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location point of the camera.
         * @return the Builder object itself for method chaining.
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param vTo the forward direction vector.
         * @param vUp the up direction vector.
         * @return the Builder object itself for method chaining.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         * @param width the width of the view plane.
         * @param height the height of the view plane.
         * @return the Builder object itself for method chaining.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("View plane dimensions must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         * @param distance the distance between the camera and the view plane.
         * @return the Builder object itself for method chaining.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("View plane distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds the Camera object.
         * @return the constructed Camera object.
         * @throws MissingResourceException if any required field is missing.
         */
        public Camera build() {

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            if (camera.location == null) {
                throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "direction vectors");
            }
            if (camera.width == 0.0 || camera.height == 0.0) {
                throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "view plane size");
            }
            if (camera.distance == 0.0) {
                throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "view plane distance");
            }

            return (Camera) camera;//.clone();
        }
    }
}
