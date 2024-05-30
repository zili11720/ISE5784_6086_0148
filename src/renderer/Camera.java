package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
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

    //The image writer for writing the rendered image
    private ImageWriter imageWriter;
    //The ray tracer for tracing the rays from the camera to the scene
    private RayTracerBase rayTracer;

    /**
     * Default private constructor
     */
    private Camera() {}


    /**
     * Static method to get a new Builder object for constructing a Camera
     *
     * @return a new Builder object
     */
    public static Builder getBuilder() { return new Builder();}

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
        if(!isZero(xJ))  PIJ = PIJ.add(vRight.scale(xJ));//move right ass needed
        if(!isZero(yJ))  PIJ = PIJ.add(vUp.scale(yJ));//move left as needed
        //else stay in thr middle

        return new Ray(location, PIJ.subtract(location));
    }

    /**
     * Internal class camera implements Builder design pattern
     */
    public static class Builder {

        private final Camera camera = new Camera();

        /**
         * Set the location of the camera.
         *
         * @param location the location point of the camera.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the location is null
         */
        public Builder setLocation(Point location) throws IllegalArgumentException{
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.location = location;
            return this;
        }

        /**
         * Set the direction vectors of the camera.
         *
         * @param vTo the forward direction vector.
         * @param vUp the up direction vector.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the vectors are null or not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) throws IllegalArgumentException{
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
         * Set the size of the view plane.
         * @param width the width of the view plane.
         * @param height the height of the view plane.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the dimensions aren't positive
         */
        public Builder setVpSize(double width, double height) throws IllegalArgumentException  {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("View plane dimensions must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Set the distance between the camera and the view plane.
         * @param distance the distance between the camera and the view plane.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the distance is not positive
         */
        public Builder setVpDistance(double distance) throws IllegalArgumentException {
            if (distance <= 0) {
                throw new IllegalArgumentException("View plane distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the image writer for the camera.
         *
         * @param imageWriter The image writer to set.
         * @return This camera instance.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null) {
                throw new IllegalArgumentException("ImageWriter cannot be null");
            }
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the camera.
         *
         * @param rayTracer The ray tracer to set.
         * @return This camera instance.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null) {
                throw new IllegalArgumentException("RayTracer cannot be null");
            }
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Builds the Camera object.
         * @return the constructed Camera object.
         * @throws MissingResourceException if any required field is missing.
         */
        public Camera build() throws MissingResourceException {

            String missingData="Missing rendering data";

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            if (camera.location == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "location");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "direction vectors");
            }
            if (camera.width == 0.0 || camera.height == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "view plane size");
            }
            if (camera.distance == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "view plane distance");
            }
            if(camera.imageWriter == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "imageWriter");
            }
            if(camera.rayTracer == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "rayTracer");
            }

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Renders the image by iterating through each pixel in the view plane and
     * calling castray for each pixel
     * @throws MissingResourceException if either the image writer or the ray tracer base are not set.
     */
    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "imageWriter");

        if (rayTracer == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "rayTracer");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int j = 0; j < nX; j++) {
            for (int i = 0; i < nY; i++) {
                castRay(j, i, nX, nY);
            }
        }
        return this;
    }
    /**
     * Writes a grid of pixels to the image writer, with a given interval between
     * the grid lines and a specified color.
     * @throws  MissingResourceException if the image writer is not set
     * @param interval the interval between grid lines
     * @param color    the color to use for the grid lines
     */
    public Camera printGrid(int interval, primitives.Color color){
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");
        // === running on the view plane===//
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int j = 0; j < nX; ++j)
            for (int i = 0; i < nY; ++i)
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
        return this;
    }

    /**
     * Writes the image to file by delegating to image writer
     * @throws MissingResourceException if the image writer is not set.
     */
    public Camera writeToImage() throws MissingResourceException {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "ImageWriter");
        imageWriter.writeToImage();
        return this;
    }

    /**
     *Casts a ray through a specified pixel and coloring the pixel using the imageWriter
     * @param j The x-coordinate of the pixel
     * @param i The y-coordinate of the pixel
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     */
    private void castRay(int j, int i,int nX, int nY) {
        Ray ray = constructRay(nX,nY,j,i);
        Color color= this.rayTracer.traceRay(ray);
        this.imageWriter.writePixel(j, i, color);
    }
}
