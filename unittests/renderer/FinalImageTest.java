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
                .setNumOfRays(1000)
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void teddyBearImage() {
        final Scene scene         = new Scene("Final Test scene");

        //materials for the scene
        Material surfaceM = new Material().setKd(0.2).setKs(2),
                eyesM=new Material(),
                cloudM = new Material().setKd(2).setKs(0.6).setkT(10),
                teddyM = new Material().setKd(1).setKs(6).setkR(0.08).setShininess(300);

        Color cloudColor = new Color(255, 178, 200);

        //surface---------------------------------------------------------------------------
        scene.geometries.add(
                new Triangle(new Point(-500, -500, 0), new Point(150, -150, -200),
                        new Point(75, 75, -230)).setEmission(new Color(15, 220, 220)).setMaterial(surfaceM),
                new Triangle(new Point(-150, -150, 0), new Point(-70, 70, -200),
                        new Point(75, 75, -230)).setEmission(new Color(15, 220, 220)).setMaterial(surfaceM));

        //teddy bear------------------------------------------------------------------------------
        Color teddy=new Color(231, 84, 128);
        scene.geometries.add(new Sphere(20d,new Point(0, 52, 200)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(28d,new Point(0, 10, 200)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(16d,new Point(0, 10, 220)).setEmission(new Color(255,255,100)));
        //ears
        scene.geometries.add(new Sphere(7d,new Point(-15, 70, 190)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(7d,new Point(15, 70, 190)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(4,new Point(-15, 70, 195)).setEmission(Color.BLACK).setMaterial(teddyM));
        scene.geometries.add(new Sphere(4d,new Point(15, 70, 195)).setEmission(Color.BLACK).setMaterial(teddyM));
        //ands
        scene.geometries.add(new Sphere(10d,new Point(-30, 25, 180)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(10d,new Point(30, 25, 180)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(6d,new Point(-33, 25, 200)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(6d,new Point(33, 25, 200)).setEmission(teddy).setMaterial(teddyM));
        //legs
        scene.geometries.add(new Sphere(12d,new Point(-15, -20, 200)).setEmission(teddy).setMaterial(teddyM));
        scene.geometries.add(new Sphere(12d,new Point(15, -20, 200)).setEmission(teddy).setMaterial(teddyM));
        //eyes
        scene.geometries.add(new Sphere(4d,new Point(-7, 55, 220)).setEmission(Color.BLACK).setMaterial(eyesM));
        scene.geometries.add(new Sphere(4d,new Point(7, 55, 220)).setEmission(Color.BLACK).setMaterial(eyesM));
        scene.geometries.add(new Sphere(3d,new Point(-7, 54, 225)).setEmission(cloudColor));
        scene.geometries.add(new Sphere(3d,new Point(7, 54, 225)).setEmission(cloudColor));
        scene.geometries.add(new Sphere(2d,new Point(-7, 53, 230)).setEmission(Color.BLACK));
        scene.geometries.add(new Sphere(2d,new Point(7, 53, 230)).setEmission(Color.BLACK));
        //nose
        scene.geometries.add(new Triangle(
                        new Point(0, 45, 220),
                        new Point(-2.6, 49, 220),
                        new Point(2.6, 49, 220)).setEmission(Color.BLACK).setMaterial(eyesM));
        //mouse
        scene.geometries.add(new Sphere(2d,new Point(0, 41, 220)).setEmission(Color.BLACK));
        //clouds----------------------------------------------------------------------

        // Cloud 1
        scene.geometries.add(new Sphere(10d, new Point(-50, 60, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(-58, 60, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(-42, 60, 100)).setEmission(cloudColor).setMaterial(cloudM));
        // Cloud 2
        scene.geometries.add(new Sphere(10d, new Point(60, 65, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(52, 65, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(68, 65, 100)).setEmission(cloudColor).setMaterial(cloudM));
        // Cloud 3
        scene.geometries.add(new Sphere(10d, new Point(80, 35, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(72, 35, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(88, 35, 100)).setEmission(cloudColor).setMaterial(cloudM));

        // Cloud 4
        scene.geometries.add(new Sphere(10d, new Point(-72, 40, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(-80, 40, 100)).setEmission(cloudColor).setMaterial(cloudM));
        scene.geometries.add(new Sphere(8d, new Point(-66, 40, 100)).setEmission(cloudColor).setMaterial(cloudM));

        // Swing ----------------------------------------------------------------------------------
        scene.geometries.add(new Tube(1d, new Ray(new Point(35, 200, 190), new Vector(0, 1, 0))).setEmission(new Color(101, 67, 133)));

        scene.geometries.add(new Tube(1d, new Ray(new Point(-35, 200, 190), new Vector(0, 1, 0))).setEmission(new Color(101, 67, 133)));

        scene.geometries.add(new Polygon(
                new Point(-50, -10, 200),
                new Point(50, -10, 200),
                new Point(50, -20, 200),
                new Point(-50, -20, 200)).setEmission(new Color(101, 67, 33)));

        //lights--------------------------------------------------------------
        scene.setAmbientLight(new AmbientLight(new Color(255,0,0), 0.15));
        scene.lights.add(
                new SpotLight(new Color(60, 0, 0), new Point(0, 10, 800), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(255, 100, 0), new Point(-30, 10, 230))
                .setkL(0.01).setkQ(0.0002));
        scene.lights.add(new DirectionalLight(new Color(40, 0, 0), new Vector(0, 1, 0)));

        final Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setRayTracer(new SimpleRayTracer(scene));
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("teddyBear", 600, 600))
                .setNumOfRays(1000)
                .setadaptive(true)
                .setMultithreading(100)
                .build()
                .renderImage()
                .writeToImage();
    }
}
