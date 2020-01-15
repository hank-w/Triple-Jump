package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheetController {
	public static String path = Main.class.getResource("").toString();
	
	public static BufferedImage playerSpriteSheet = loadImage("playerSpriteSheet");
	
	public static BufferedImage loadImage(String name) {
		URL url = null;
		try {
			url = new URL(path + "resources/images/" + name + ".png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		BufferedImage res = toBufferedImage(img);
		return res;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public static int[] spriteSheetCoord(BufferedImage img, int phase, int rowN, int colN) {
		double width = img.getWidth();
		double height = img.getHeight();
		
		double indW = width/rowN;
		double indH = height/colN;
		
		double x1 = (phase % rowN) * indW;
		double y1 = (phase/rowN) * indH;
		double x2 = x1+indW;
		double y2 = y1+indH;
		return new int[] {(int)x1,(int)y1,(int)x2,(int)y2};
	}
}
