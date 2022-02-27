package imageWrapper;

// Immutable class providing syntactic sugar around pixel data
public class Pixel {
	final private int m_r;
	final private int m_g;
	final private int m_b;
	
	// Args: 32 bit pixel represented as 0xaarrggbb
	// we're just gonna ignore the alpha value   
	public Pixel(int nPixel)
	{
		m_r = (nPixel & 0x00FF0000) >> 16;
		m_g = (nPixel & 0x0000FF00) >> 8;
		m_b = (nPixel & 0x000000FF);
	}
	
	public Pixel(int r, int g, int b)
	{
		if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
		{
			throw new IllegalArgumentException("rgb values must be between 0 and 255");
		}
		
		m_r = r;
		m_g = g;
		m_b = b;
	}
	
	final public int r()
	{
		return m_r;
	}
	
	final public int g()
	{
		return m_g;
	}
	
	final public int b()
	{
		return m_b;
	}
	
	public int asInt()
	{
		// we validate rgb values are bound upon creation, but mod them by 256 just to make sure
		int nResult = 0;
		nResult += 255 << 24; // set the alpha channel to fully opaque
		nResult += (m_r % 256) << 16;
		nResult += (m_g % 256) << 8;
		nResult += (m_b % 256);
		return nResult;
	}
}
