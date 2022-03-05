package imageWrapper;

import java.awt.image.BufferedImage;

public class ImageWrapper {

	private Pixel[][] m_data;
	
	// Initializes a white image with the supplied dimensions
	public ImageWrapper(int width, int height)
	{
		m_data = initPixelArray(width, height);
		final var white = new Pixel(255, 255, 255); 
		fill(white);
	}
	
	// Initializes a colored image with the provided dimensions
	public ImageWrapper(int width, int height, Pixel color)
	{
		m_data = initPixelArray(width, height);
		fill(color);
	}
	
	// Initializes with data retrieved from BufferedImage
	public ImageWrapper(BufferedImage image)
	{
		m_data = initPixelArray(image.getWidth(), image.getHeight());
		for (int y = 0; y < getHeight(); ++y)
		{
			for (int x = 0; x < getWidth(); ++x)
			{
				Pixel p = new Pixel(image.getRGB(x, y));
				m_data[y][x] = p;
			}
		}
	}
	
	public Pixel getPixel(int x, int y)
	{
		return m_data[y][x];
	}
	
	public void setPixel(int x, int y, Pixel p)
	{
		if (y < 0 || y >= m_data.length)
			throw new IllegalArgumentException("y value outside boundary!");
		
		var row = m_data[y];
		if (x < 0 || x >= row.length)
			throw new IllegalArgumentException("x value outside boundary!");
		
		m_data[y][x] = p;
	}
	
	public Double getEnergy(int x, int y)
	{
		return m_data[y][x].energy();
	}
	
	public void setEnergy(int x, int y, double energy)
	{
		Pixel p = m_data[y][x];
		m_data[y][x] = new Pixel(p.r(), p.g(), p.b(), energy);
	}
	
	public int getWidth()
	{
		return m_data[0].length;
	}
	
	public int getHeight()
	{
		return m_data.length;
	}
	
	public void removeHorizontalSeam(int[] seam)
	{
		if (seam.length != getWidth())
			throw new IllegalArgumentException("horizontal seam length must equal image width.");
		
		var result = initPixelArray(getWidth(), getHeight() - 1);
		
		for (int x = 0; x < getWidth(); ++x)
		{
			int nIndexToSkip = seam[x];
			int nResultY = 0;
			for (int y = 0; y < getHeight(); ++y)
			{
				if (y != nIndexToSkip)
				{
					result[nResultY][x] = m_data[y][x];
					++nResultY;
				}
			}
		}
		
		m_data = result;
	}
	
	public void removeVerticalSeam(int[] seam)
	{
		if (seam.length != getHeight())
			throw new IllegalArgumentException("vertical seam length must equal image height.");
		
		var result = initPixelArray(getWidth() - 1, getHeight());
		
		for (int y = 0; y < getHeight(); ++y)
		{
			int nIndexToSkip = seam[y];
			int nResultX = 0;
			for (int x = 0; x < getWidth(); ++x)
			{
				if (x != nIndexToSkip)
				{
					result[y][nResultX] = m_data[y][x];
					++nResultX;
				}
			}
		}
		
		m_data = result;
	}
	
	public BufferedImage currentContentsAsImage()
	{
		// the provided type makes our pixel values compatible
		var result = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for (int y = 0; y < getHeight(); ++y)
		{
			for (int x = 0; x < getWidth(); ++x)
			{
				result.setRGB(x,  y, m_data[y][x].asInt());
			}
		}
		
		return result;
	}
	
	public static Pixel energyAsPixelColor(double dbEnergy, double dbMaxEnergy)
	{
		double dbPercentage = dbEnergy / dbMaxEnergy;
		int nRGB = (int)(255.0 * dbPercentage);
		return new Pixel(nRGB, nRGB, nRGB);
	}
	
	public static BufferedImage generateEnergyImage(ImageWrapper imageWrap)
	{	
		ImageWrapper result = new ImageWrapper(imageWrap.getWidth(), imageWrap.getHeight());
		
		// find max energy value in the image
		double dbMax = 0.0;
		for (int y = 0; y < imageWrap.getHeight(); ++y)
			for (int x = 0; x < imageWrap.getWidth(); ++x)
				dbMax = Math.max(dbMax, imageWrap.getEnergy(x, y));
		
		for (int y = 0; y < imageWrap.getHeight(); ++y)
		{
			for (int x = 0; x < imageWrap.getWidth(); ++x)
			{
				double dbEnergy = imageWrap.getEnergy(x, y);
				result.setPixel(x, y, energyAsPixelColor(dbEnergy, dbMax));
			}
		}
		
		return result.currentContentsAsImage();
	}
	
	static private Pixel[][] initPixelArray(int width, int height)
	{
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException("must have a width and height greater than 0");
			
		var result = new Pixel[height][];
		for (int i = 0; i < result.length; ++i)
		{
			result[i] = new Pixel[width];
		}
		
		return result;
	}
	
	private void fill(final Pixel p)
	{
		for (int row = 0; row < m_data.length; ++row)
		{
			var rowData = m_data[row];
			for (int col = 0; col < rowData.length; ++col)
			{
				rowData[col] = p;
			}
		}
	}
}
