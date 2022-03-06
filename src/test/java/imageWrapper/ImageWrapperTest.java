package imageWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageWrapperTest {
	
	ImageWrapper m_image = null;

    @Before
    public void setUp() throws Exception {
    	m_image = new ImageWrapper(3, 3);
    	double dbCounter = 0.0;
    	for (int y = 0; y < m_image.getHeight(); ++y)
    	{
    		for (int x = 0; x < m_image.getWidth(); ++x)
    		{
    			m_image.setEnergy(x, y, dbCounter);
    			dbCounter += 1.0;
    		}
    	}
    }

    @After
    public void tearDown() throws Exception {
    	m_image = null;
    }

    @Test
    public void removeStraightHorizontalSeam() {
    	// removes a horizontal seam across the top row
    	//
    	// Start  --> End
    	// 0 1 2       3 4 5
    	// 3 4 5  -->  6 7 8
    	// 6 7 8
    	
    	// constructs a horizontal seam across the top row
    	int[] seam = new int[m_image.getWidth()];
    	for (int i = 0; i < seam.length; ++i)
    		seam[i] = 0;
    	
    	m_image.removeHorizontalSeam(seam);
    	
    	// removing a seam should reduce the image's height by 1
    	assertEquals(m_image.getHeight(), 2);
    	
    	// the first row was removed so the next two rows should count up
    	// from 3 to 8
    	assertEquals(m_image.getEnergy(0, 0), 3.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 0), 4.0, 0.00001);
    	assertEquals(m_image.getEnergy(2, 0), 5.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 1), 6.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 1), 7.0, 0.00001);
    	assertEquals(m_image.getEnergy(2, 1), 8.0, 0.00001);
    }
    
    @Test 
    public void removeDiagonalHorizontalSeam() {

    	// removes a diagonal seam from top left to bottom right
    	// contents should move upward in columns
    	//
    	// Start  --> End
    	// 0 1 2       3 1 2
    	// 3 4 5  -->  6 7 5
    	// 6 7 8
    	
    	// constructs a diagonal seam from top left to bottom right
    	int[] seam = new int[m_image.getWidth()];
    	for (int i = 0; i < seam.length; ++i)
    		seam[i] = i;
    	
    	m_image.removeHorizontalSeam(seam);
    	
    	// removing a seam should reduce the image's height by 1
    	assertEquals(m_image.getHeight(), 2);
    	
    	assertEquals(m_image.getEnergy(0, 0), 3.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 0), 1.0, 0.00001);
    	assertEquals(m_image.getEnergy(2, 0), 2.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 1), 6.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 1), 7.0, 0.00001);
    	assertEquals(m_image.getEnergy(2, 1), 5.0, 0.00001);
    }

    @Test
    public void removeStraightVerticalSeam() {
    	
    	// removes a vertical seam down first column
    	//
    	// Start  --> End
    	// 0 1 2       1 2
    	// 3 4 5  -->  4 5
    	// 6 7 8       7 8
    	
    	// constructs a horizontal seam across the top row
    	int[] seam = new int[m_image.getWidth()];
    	for (int i = 0; i < seam.length; ++i)
    		seam[i] = 0;
    	
    	m_image.removeVerticalSeam(seam);
    	
    	// removing a seam should reduce the image's width by 1
    	assertEquals(m_image.getWidth(), 2);
    	
    	// the first row was removed so the next two rows should count up
    	// from 3 to 8
    	assertEquals(m_image.getEnergy(0, 0), 1.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 0), 2.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 1), 4.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 1), 5.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 2), 7.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 2), 8.0, 0.00001);
    }
    
    @Test
    public void removeDiagonalVerticalSeam() {
    	// Removes a diagonal seam from top left
    	// to bottom right contents should move
    	// horizontally left in rows
    	//
    	// Start  --> End
    	// 0 1 2       1 2
    	// 3 4 5  -->  3 5
    	// 6 7 8       6 7
    	
    	// constructs a horizontal seam across the top row
    	int[] seam = new int[m_image.getWidth()];
    	for (int i = 0; i < seam.length; ++i)
    		seam[i] = i;
    	
    	m_image.removeVerticalSeam(seam);
    	
    	// removing a seam should reduce the image's width by 1
    	assertEquals(m_image.getWidth(), 2);
    	
    	// the first row was removed so the next two rows should count up
    	// from 3 to 8
    	assertEquals(m_image.getEnergy(0, 0), 1.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 0), 2.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 1), 3.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 1), 5.0, 0.00001);
    	
    	assertEquals(m_image.getEnergy(0, 2), 6.0, 0.00001);
    	assertEquals(m_image.getEnergy(1, 2), 7.0, 0.00001);
    }
}