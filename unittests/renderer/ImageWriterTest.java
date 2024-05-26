package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test the writeToImage() method of the ImageWriter class. This test creates an
     * image with alternating red and light blue pixels, with red pixels at every
     * 50th column and row.
     */

    @Test
    void test1() {
        Color lightBlue = new Color(173, 216, 230);
        Color red = new Color(255, 0, 0);
        ImageWriter imageWriter = new ImageWriter("pixels", 800, 500);
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int j = 0; j < nX; j++)
            for (int i = 0; i < nY; i++)
                imageWriter.writePixel(j, i, (j % 50 == 0) || (i % 50 == 0) ? red : lightBlue);
        imageWriter.writeToImage();
    }


}
