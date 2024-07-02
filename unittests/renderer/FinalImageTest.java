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
        final Scene scene         = new Scene("Final Test scene");

        Material groundM = new Material().setKd(0.6).setKs(0.4),
                seaM = new Material().setKd(0.2).setKs(2).setShininess(3000).setkR(5).setkT(0.8),
                rockM = new Material().setKd(0.2),
                cloudM = new Material().setKd(0.5).setKs(0.6).setShininess(300).setkT(10),
                sunM = new Material().setKd(0.2).setKs(4).setShininess(900).setkT(3);

        // Ground
        scene.geometries.add(new Polygon(
                new Point(-500, -500, 0),
                new Point(500, -500, 0),
                new Point(500, -100, 0),
                new Point(-500, -100, 0)
        ).setEmission(new Color(101, 67, 33)).setMaterial(groundM));

        //sea
        scene.geometries.add(new Polygon(
                new Point(-500, -400, 0),
                new Point(500, -400, 0),
                new Point(500, -100, 10),
                new Point(-500, -100, 10)
        ).setEmission(new Color(0,0,255)).setMaterial(seaM));

        //sun
        scene.geometries.add(new Sphere(40d,new Point(-100, 140, 100)).setEmission(new Color(255, 200, 20)).setMaterial(sunM));

        // Swing (Tube as a straight line from top to bottom)
        scene.geometries.add(new Tube(3d, new Ray(new Point(100, -100, -100), new Vector(0, -1, 0))).setEmission(new Color(101, 67, 133)));

        scene.geometries.add(new Tube(3d, new Ray(new Point(200, -20, -40), new Vector(0, -1, 0))).setEmission(new Color(101, 67, 133)));

        scene.geometries.add(new Polygon(
                new Point(60, -130, 100),
                new Point(230, -130, 100),
                new Point(230, -100, 100),
                new Point(60, -100, 100)).setEmission(new Color(101, 67, 33)));

        // ---------------Clouds--------------------------
        Color cloudColor = new Color(255, 178, 200);

        // Cloud 1
        scene.geometries.add(new Sphere(30d, new Point(165, 165, -90)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(140, 165, -90)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(190, 165, -90)).setEmission(cloudColor).setMaterial(cloudM));
        // Cloud 2
        scene.geometries.add(new Sphere(30d, new Point(-215, 325, 135)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(-190, 325, 125)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(-240, 325, 125)).setEmission(cloudColor).setMaterial(cloudM));
        // Cloud 3
        scene.geometries.add(new Sphere(30d, new Point(35, 300, 165)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(10, 300, 155)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(60, 300, 155)).setEmission(cloudColor).setMaterial(cloudM));
        // Cloud 4
        scene.geometries.add(new Sphere(30d, new Point(-370, 80, 135)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(-345, 80, 125)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(-395, 80, 125)).setEmission(cloudColor).setMaterial(cloudM));

        // Cloud 4
        scene.geometries.add(new Sphere(30d, new Point(370, 325, 135)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(345, 325, 125)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(20d, new Point(395, 325, 125)).setEmission(cloudColor).setMaterial(cloudM));

       //-----------------------end clouds----------------------------------

        //rocks
        Color grey = new Color(128, 128, 128);

        scene.geometries.add(new Triangle(new Point(-400,-100,0), new Point(-300, 50, 0), //
                new Point(-80, -100, 0)).setEmission(grey).setMaterial(rockM));
        scene.geometries.add(new Triangle(new Point(-360,-100,100), new Point(-250, 30, 100), //
                new Point(-100, -100, 100)).setEmission(Color.BLACK).setMaterial(rockM));

        //boat
        scene.geometries.add(new Triangle(new Point(-500,-300,200), new Point(-420, -20, 200), //
                new Point(-380, -300, 200)).setEmission(grey).setMaterial(cloudM));

        scene.geometries.add(new Polygon(
                new Point(-500, -330, 100),
                new Point(-300, -330, 100),
                new Point(-300, -300, 100),
                new Point(-500, -300, 100)).setEmission(new Color(200,140,160)));


        //---------------------------------Lights----------------------------------
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4))
                        .setkL(0.8).setkQ(2E-5));
        scene.lights.add(
                new PointLight(new Color(0, 0, 150),new Point(-95, 140, 110)).setkL(0.2).setkQ(0.2));
        scene.lights.add(
                new DirectionalLight(new Color(0, 0, 10),new Vector(-1, 1,-1 )));



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
