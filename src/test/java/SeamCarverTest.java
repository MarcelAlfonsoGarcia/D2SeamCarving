import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import imageWrapper.ImageWrapper;
import imageWrapper.Pixel;

public class SeamCarverTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void carve() {
    }

    @Test
    public void calculateImageEnergy() {
    }

    @Test
    public void calculatePixelEnergy() {
    	ImageWrapper testImage = new ImageWrapper(3, 3);
    	var blackPixel = new Pixel(0, 0, 0);
    	testImage.setPixel(0, 0, blackPixel);
    	testImage.setPixel(1, 1, blackPixel);
    	testImage.setPixel(2, 2, blackPixel);
    }

    @Test
    public void findSeam() {
    }
}