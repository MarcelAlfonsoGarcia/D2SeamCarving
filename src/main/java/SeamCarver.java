import imageWrapper.ImageWrapper;
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
		// TODO: implement this
		return 0.0;
	}
	
	// finds the "shortest path" from the top to bottom or left to right of the images
	// using the pixels' energy as the weight for each step of the path
	public static int[] findSeam(boolean bHorizontalSeam, final ImageWrapper imageWrap)
	{
		return null;
	}
}
