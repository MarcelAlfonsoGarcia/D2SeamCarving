
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

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
    public void testCalculateImageEnergy() {
    	
    	ImageWrapper test = new ImageWrapper(3,3);
    	var blackPixel = new Pixel(0, 0, 0);
    	test.setPixel(0, 0, blackPixel);
    	test.setPixel(1, 1, blackPixel);
    	test.setPixel(2, 2, blackPixel);
    	SeamCarver.calculateImageEnergy(test);
    	
    	double energy = test.getEnergy(1, 1);
    	assertEquals("pixel (0,0) has wrong energy", 0.0, energy, 0.0000);
    	
    	
    }

    
	@Test
    public void testCalculatePixelEnergy() {
    	ImageWrapper testImage = new ImageWrapper(3, 3); //filled white
    	var blackPixel = new Pixel(0, 0, 0);
    	testImage.setPixel(0, 0, blackPixel);
    	testImage.setPixel(1, 1, blackPixel);
    	testImage.setPixel(2, 2, blackPixel);
    	double energy = SeamCarver.calculatePixelEnergy(testImage,0,0);
    	assertEquals("pixel (0,0) has wrong energy", 0.0, energy, 0.0000);
    	energy = SeamCarver.calculatePixelEnergy(testImage, 1, 1);
    	assertEquals("pixel (1,1) has wrong energy", 0.0, energy, 0.0000);
    	energy = SeamCarver.calculatePixelEnergy(testImage, 2, 2);
    	assertEquals("pixel (2,2) has wrong energy", 0.0, energy, 0.0000);
    	energy = SeamCarver.calculatePixelEnergy(testImage, 1, 0);
    	assertEquals("pixel (1,0) has wrong energy", 1081.8733, energy-(energy%0.0001), 0.0000);
    	energy = SeamCarver.calculatePixelEnergy(testImage, 2, 0);
    	assertEquals("pixel (2,0) has wrong energy", 1081.8733, energy-(energy%0.0001), 0.0000);
    	
    	ImageWrapper testImage2 = new ImageWrapper(3, 3);
    	var testPixel1 = new Pixel(0,255,0);
    	var testPixel2 = new Pixel(255,0,255);
    	assertEquals("pixel1 has wrong r value after creating new pixel", 0, testPixel1.r());
    	assertEquals("pixel2 has wrong r value after creating new pixel", 255, testPixel2.r());
    	testImage2.setPixel(1, 0, testPixel1);
    	testImage2.setPixel(1, 2, testPixel2);
    	testImage2.setPixel(0, 1, testPixel1); //left pixel
    	testImage2.setPixel(2, 1, testPixel2); //right pixel
    	energy = SeamCarver.calculatePixelEnergy(testImage2, 1, 1);
    	assertEquals("pixel (1,1) has wrong energy", 1081.8733, energy-(energy%0.0001), 0.0000);
    	energy = SeamCarver.calculatePixelEnergy(testImage2, 0, 2);
    	assertEquals("pixel (0,2) has wrong energy", 570.1973, energy-(energy%0.0001), 0.0000);
    	
    	
    }

    @Test
    public void testFindSeam() {
    	ImageWrapper testImage = new ImageWrapper(3, 3);
    	testImage.setEnergy(0, 0, 0);
    	testImage.setEnergy(1, 0, 0);
    	testImage.setEnergy(2, 0, 0);
    	
    	testImage.setEnergy(0, 1, 0);
    	testImage.setEnergy(1, 1, 0);
    	testImage.setEnergy(2, 1, 0);
    	
    	testImage.setEnergy(0, 2, 0);
    	testImage.setEnergy(1, 2, 0);
    	testImage.setEnergy(2, 2, 0);
    	
    	int[] vseam = SeamCarver.findSeam(false,testImage);
    	int[] vexpected = new int[] {0,0,0};
    	assertArrayEquals("incorrect vertical seam", vexpected, vseam);
    	int[] hseam = SeamCarver.findSeam(true, testImage);
    	int[] hexpected = new int[] {0,0,0};
    	assertArrayEquals("incorrect horizontal seam", hexpected, hseam);
    	
    	ImageWrapper testImage2 = new ImageWrapper(3, 4);
    	
    	testImage2.setEnergy(0, 0, 0);
    	testImage2.setEnergy(1, 0, 2);
    	testImage2.setEnergy(2, 0, 2);
    	
    	testImage2.setEnergy(0, 1, 2);
    	testImage2.setEnergy(1, 1, 0);
    	testImage2.setEnergy(2, 1, 2);
    	
    	testImage2.setEnergy(0, 2, 2);
    	testImage2.setEnergy(1, 2, 2);
    	testImage2.setEnergy(2, 2, 0);
    	
    	testImage2.setEnergy(0, 3, 0);
    	testImage2.setEnergy(1, 3, 0);
    	testImage2.setEnergy(2, 3, 0);
    	
    	int[] vseam2 = SeamCarver.findSeam(false, testImage2);
    	int[] vexpected2 = new int[] {0,1,2,2};
    	assertArrayEquals("incorrect vertical2 seam", vexpected2, vseam2);
    	int[] hseam2 = SeamCarver.findSeam(true, testImage2);
    	int[] hexpected2 = new int[] {0,1,2};
    	assertArrayEquals("incorrect horizontal2 seam", hexpected2, hseam2);
    }
    
}