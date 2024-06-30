package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class FinalImageTest {

    @Test
    public void finalImage() {
        /** Scene for the test */
        final Scene scene         = new Scene("Final Test scene")
                .setBackground(new Color( 255, 99, 71));//pink
                //.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.1));

        Material groundM = new Material().setKd(0.6).setKs(0.4).setShininess(200),
                snowM = new Material().setKd(0.3).setKs(0.6).setShininess(200).setkR(0.6),
                seaM = new Material().setKd(0.2).setKs(0.9).setShininess(3000).setkR(0.6),
                birdM = new Material().setKd(0.2).setKs(0.6).setShininess(300),
                cloudM = new Material().setKd(0.5).setKs(0.6).setShininess(3000),
                bushM = new Material().setKd(0.2).setKs(0.1).setShininess(300),
                 sunM = new Material().setKd(0.2).setKs(0.2).setShininess(900).setkT(0.6);

        // Sea
        scene.geometries.add(new Polygon(
                new Point(-500, -500, 0),
                new Point(500, -500, 0),
                new Point(500, -100, 0),
                new Point(-500, -100, 0)
        ).setEmission(new Color(101, 67, 33)).setMaterial(groundM));

        // Ground (lower than the sea)
        scene.geometries.add(new Polygon(
                new Point(-500, -400, 0),
                new Point(500, -400, 0),
                new Point(500, -100, 10),
                new Point(-500, -100, 10)
        ).setEmission(new Color(30, 150, 255)).setMaterial(seaM));

        //sun
        scene.geometries.add(new Sphere(40d,new Point(-100, 140, 100)).setEmission(new Color(255, 200, 20)).setMaterial(sunM));

        // Swing (Tube as a straight line from top to bottom)
        scene.geometries.add(new Tube(2d, new Ray(new Point(40, 40, 40), new Vector(0, -1, 0))));

        scene.geometries.add(new Tube(2d, new Ray(new Point(100, 100, 100), new Vector(0, -1, 0))));

        /** Camera builder for the test */
        final Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setRayTracer(new SimpleRayTracer(scene));
        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("finalImage", 400, 400))
                .build()
                .renderImage()
                .writeToImage();
    }
}
