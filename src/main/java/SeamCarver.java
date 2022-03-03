import imageWrapper.ImageWrapper;
import imageWrapper.Pixel;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class SeamCarver {
	
	public static List<BufferedImage> carve(boolean bHorizontalSeam, int nCarves, ImageWrapper imageWrap)
	{
		final int maxImages = 100;
		int saveImageEveryN = 1;
		if (nCarves > maxImages)
		{
			saveImageEveryN = (int)Math.floor((double)nCarves / (double)maxImages);
		}
		
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		
		// add the initial image so we can see where the image started
		images.add(imageWrap.currentContentsAsImage());
		
		for (int i = 0; i < nCarves; ++i)
		{
			// first calculate the energy of each pixel in the image
			calculateImageEnergy(imageWrap);
			
			// Next find a seam
			int[] seamToRemove = findSeam(bHorizontalSeam, imageWrap);
			
			// Now remove the seam we found from the image
			if (bHorizontalSeam)
				imageWrap.removeHorizontalSeam(seamToRemove);
			else
				imageWrap.removeVerticalSeam(seamToRemove);
			
			if (i % saveImageEveryN == 0 || i == (nCarves - 1))
				images.add(imageWrap.currentContentsAsImage());
		}
		
		return images;
	}
	
	// iterates over every pixel in the image and calculates each pixel's energy
	public static void calculateImageEnergy(ImageWrapper imageWrap)
	{
		for (int y = 0; y < imageWrap.getHeight(); ++y)
		{
			for (int x = 0; x < imageWrap.getWidth(); ++x)
			{
				double pixelEnergy = calculatePixelEnergy(imageWrap, x, y);
				imageWrap.setEnergy(x, y, pixelEnergy);
			}
		}
	}
	
	// calculates energy of a pixel at a given x and y
	public static double calculatePixelEnergy(ImageWrapper imageWrap, int x, int y)
	{
		int height = imageWrap.getHeight()-1;
		int width = imageWrap.getWidth()-1;
		
		if (y > height || y < 0 || x > width || x < 0) 
			throw new IllegalArgumentException("coordinates provided out of bounds for image!");
		
		//get pixel returns m_data[y][x] so use Princeton coordinates
		
		Pixel aboveP;
		Pixel belowP; 
		
		Pixel rightP; 
		Pixel leftP;
		
		//handles edge cases where pixel is at bottom or top of image
		if (y == height) {
			aboveP = imageWrap.getPixel(x, y-1);
			belowP = imageWrap.getPixel(x, 0);
		}
		else if (y == 0) {
			aboveP = imageWrap.getPixel(x, height);
			belowP = imageWrap.getPixel(x, y+1);
		}
		//handles case where pixel is somewhere between top and bottom;
		else {
			aboveP = imageWrap.getPixel(x, y-x);
			belowP = imageWrap.getPixel(x, y+1);
		}
		
		//handle edge cases where pixel is at far right or far left side of image
		if (x == width) {
			rightP = imageWrap.getPixel(x-1, y);
			leftP  = imageWrap.getPixel(0, y);
		}
		else if (x == 0) {
			rightP = imageWrap.getPixel(width, y);
			leftP  = imageWrap.getPixel(x+1, y);
		}
		//handles case where pixel is somewhere between left most and right most side of image;
		else {
			rightP = imageWrap.getPixel(x-1, y);
			leftP  = imageWrap.getPixel(x+1,y);
		}
		
		int rX = rightP.r() - leftP.r();
		int gX = rightP.g() - leftP.g();
		int bX = rightP.b() - leftP.b();
		
		int xtot = rX + gX + bX;
		
		int rY = belowP.r() - aboveP.r();
		int gY = belowP.g() - aboveP.g();
		int bY = belowP.b() - aboveP.b();
		
		int ytot = rY + gY + bY;
		
		Double xSq = Math.pow((double)xtot, 2);
		Double ySq = Math.pow((double)ytot, 2);
		
		Double energy = Math.sqrt(xSq + ySq);
		
		return energy;
	}
	
	// finds the "shortest path" from the top to bottom or left to right of the images
	// using the pixels' energy as the weight for each step of the path
	public static int[] findSeam(boolean bHorizontalSeam, final ImageWrapper imageWrap)
	{
		double[][] minEnergyForPath = new double [imageWrap.getHeight()] [imageWrap.getWidth()] ;
		
		int[][] directionData = new int [imageWrap.getHeight()] [imageWrap.getWidth()] ;
		
		int[] seam;
		
		int left = -1;
		int cntr = 0;
		int right = 1;
		
		
		if(bHorizontalSeam == true) 
		{
			//TODO: HANDLE CASE FOR HORIZONTAL findSeam
			seam = new int[imageWrap.getWidth()];
		}
		else {
			
			seam = new int[imageWrap.getHeight()];
			//creating minEnergyForPath array and directionData array (make a separate function?)
			
			//poor memory access????
			for (int y = imageWrap.getHeight()-1; y >= 0; y--) 
			{
				
				for (int x = 0; x<imageWrap.getWidth(); x++) 
				{
					//initialize bottom row of minEnergyForPath with energy values;
					if (y == imageWrap.getHeight()-1) 
					{
						minEnergyForPath[y][x] = imageWrap.getEnergy(x, y);
					}
					else 
					{
						minEnergyForPath[y][x] = imageWrap.getEnergy(x, y);
				
						Double energyCntr = minEnergyForPath[y+1][x+cntr];
						Double energyLeft; 
						Double energyRight;
						
						
						//handles case with no left because we're at the left most side of image
						if (x == 0) 
						{
							energyLeft = null;
							energyRight= minEnergyForPath[y+1][x+right];
							
							//case 1 (right < center, left doesn't exist)
							if (energyRight < energyCntr) 
								directionData[y][x] = right;
							
							//case 2 (center <= right, left doesn't exist)
							else //tie goes to center
								directionData[y][x] = cntr;
							
							
						}
						
						//handles case with no right because we're at the right most side of image
						else if (x == imageWrap.getWidth()-1) 
						{
							energyLeft = minEnergyForPath[y+1][x+left];
							energyRight= null;
							
							//case 3 (left < center, right doesn't exist)
							if (energyLeft < energyCntr) 
								directionData[y][x] = left;
							
							//case 4 (center <= left, right doesn't exist)
							else //tie goes to center
								directionData[y][x] = cntr;
							
							
						}
						
						//handles case with both left and right, somewhere in middle of image
						else 
						{
							energyLeft = minEnergyForPath[y+1][x+left];
							energyRight= minEnergyForPath[y+1][x+right];
							
							boolean rightWins;
							
							
							if (energyRight < energyLeft) 
								rightWins = true;
							
							else //tie goes to left 
								rightWins = false;
							
							
							if (rightWins == true) 
							{
								//case 5 (right < left and right < center)
								if (energyRight < energyCntr) 
									directionData[y][x] = right;
								
								//case 6 (right < left but center <= right)
								else //tie goes to center
									directionData[y][x] = cntr;
								
							}
							else 
							{ 
								//case 7 (left <= right and left < center)
								if (energyLeft < energyCntr) 
									directionData[y][x] = left;
								
								//case 8 (left <= right but center <= left)
								else  //tie goes to center
									directionData[y][x] = cntr;
								
							}
							
							
						}
						int direction = directionData[x][y];
						minEnergyForPath[y][x] += minEnergyForPath[y+1][x+direction]; 
						
					}
				}
			}
			
			//find start pixel at top;
			double startPixelPathEnergy = minEnergyForPath[0][0];
			int startPixelIndex = 0;
			
			//inefficient search but only need to do this with top row of pixels.
			for(int x = 1; x<imageWrap.getWidth(); x++) 
			{
				if(minEnergyForPath[0][x] < startPixelPathEnergy) 
				{ 
					startPixelPathEnergy = minEnergyForPath[0][x];
					startPixelIndex = x;
				}
				
			}
			
			//filling seam[] with values. 
			//the index of the seam is the row of the pixel to be removed, 
			//the value at said index is the column of the pixel to be removed.
			seam[0] = startPixelIndex;
			
			for( int i = 1; i<imageWrap.getHeight(); i++) 
			{
				seam[i] = seam[i-1] + directionData[i-1][seam[i-1]]; 
			}
			
		}
		return seam;
	}
}
