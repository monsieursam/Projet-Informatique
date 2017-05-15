package traitementImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Zoom {
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    return dimg;
	}  
	public static BufferedImage zoomPlus(BufferedImage img){
		return resize(img, img.getWidth() + 100, img.getHeight() + 100);
	}
	public static BufferedImage zoomMoins(BufferedImage img){
		return resize(img, img.getWidth() - 100, img.getHeight() - 100);
	}
}
