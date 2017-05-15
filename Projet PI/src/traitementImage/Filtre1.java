package traitementImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class Filtre1  extends BMPImage {
	
	public static BufferedImage renvoyeImage(File filename){
		try{
			BufferedImage image = ImageIO.read(filename);
			return image;
		}catch(Exception e){
			return null;
		}
	}
	//Met en negatif les couleurs
	public static Image InverseColor(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) 
			{
				Color pixelcolor= new Color(image.getRGB(i,j));
				int r=pixelcolor.getRed();
				int gb=pixelcolor.getGreen();
				int b=pixelcolor.getBlue();			
				r=Math.abs(r-255);
				gb=Math.abs(gb-255);
			    b=Math.abs(b-255);		
				int rgb=new Color(r,gb,b).getRGB();		
				img.setRGB(i, j, rgb);
			}
		}
		return img;		
	}
	public static Image redFiltre(BufferedImage image) {
		
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					Color pixelcolor= new Color(image.getRGB(i,j));
					int r=pixelcolor.getRed();
					int gb=pixelcolor.getGreen();
					int b=pixelcolor.getBlue();			
					r = 255; 
					int rgb=new Color(r,gb,b).getRGB();		
					img.setRGB(i, j, rgb);
				}
			}
		  	return img;
		
	}
	public static Image blueFiltre(BufferedImage image) {
		
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					Color pixelcolor= new Color(image.getRGB(i,j));
					int r=pixelcolor.getRed();
					int gb=pixelcolor.getGreen();
					int b=pixelcolor.getBlue();	
					b = 255; 
					int rgb=new Color(r,gb,b).getRGB();		
					img.setRGB(i, j, rgb);
				}
			}
		  	return img;
		
	}
	public static Image greenFiltre(BufferedImage image) {
		
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					Color pixelcolor= new Color(image.getRGB(i,j));
					int r=pixelcolor.getRed();
					int gb=pixelcolor.getGreen();
					int b=pixelcolor.getBlue();			
					gb = 255; 
					int rgb=new Color(r,gb,b).getRGB();		
					img.setRGB(i, j, rgb);
				}
			}
		  	return img;
		
	}
	//Mettre a 90 degres
	public static Image rotation(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage img = new BufferedImage(height,width,BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++) 
		{
			for (int j = 0; j < height; j++) 
			{
				Color pixelcolor= new Color(image.getRGB(i,j));
				img.setRGB(j, i, pixelcolor.getRGB());
			}
		}
	  	return img;
	}
	//Retourne l'image
	public static Image retourneImage (BufferedImage image) {
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					Color pixelcolor= new Color(image.getRGB(i,j));
					img.setRGB(width - i - 1, height - j - 1, pixelcolor.getRGB());
				}
			}
		  	return img;
		
	}
	//renvoye le miroir de l'image
	public static Image miroirImage (BufferedImage image) {
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);	
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					Color pixelcolor= new Color(image.getRGB(i,j));
					img.setRGB(width - i - 1, j, pixelcolor.getRGB());
				}
			}
		  	return img;
	}
	//Coupe l'image en 2 et renvoye le miroir 
	public static Image miroirCouperImage (BufferedImage image) {
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				
			for (int i = 0; i < width; i++) 
			{
				for (int j = 0; j < height; j++) 
				{
					if(i < width/2){
						Color pixelcolor= new Color(image.getRGB(i,j));
						img.setRGB(i, j, pixelcolor.getRGB());
					}else{
						Color pixelcolor= new Color(image.getRGB(width - i,j));
						img.setRGB(i, j, pixelcolor.getRGB());
					}
					
				}
			}
		  	return img;
	}
	
	
	
	public static Image CONTOUR(File filename) throws IOException{		
		float[] data = {1f, 0f, -1f, 2f, 0f, -2f, 1f, 0f, -1f};
		ImgKernel a = new ImgKernel(3,3,data);
		return filtrage(a,filename);
	}
	public static Image blur(BufferedImage image) throws IOException{		
		float[] data = new float[] {
			     0.0f, -1.0f, 0.0f,
			    -1.0f, 5.0f, -1.0f,
			     0.0f, -1.0f, 0.0f
			};
		ImgKernel a = new ImgKernel(3,3,data);
		return convolver(image, a);
	}
	public static Image gaussianBlur(BufferedImage image) throws IOException{	
		float[] data = new float[] {
				 (float) 0.077847, 	(float) 0.123317, (float) 0.077847,
				 (float) 0.123317, (float) 0.195346, (float) 0.123317,
				 (float) 0.077847, 	(float) 0.123317, (float) 0.077847
			};
		ImgKernel a = new ImgKernel(3,3,data);
		
		return convolver(image,a);
	}
	
	
	public static Image convolver(BufferedImage image, ImgKernel a) throws IOException{
		BufferedImage dstImage = null;
		ConvolutionFilter op = new ConvolutionFilter(a);
		dstImage = op.filterConv(image);
		return dstImage;
	}
	
	
	
	
	public static Image filtrage(ImgKernel kernel, File filename) throws IOException{
		
		BufferedImage image = ImageIO.read(filename);
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		float[] data = kernel.getKernelData(null);
		int rgb = 0;
		for (int i = 0; i < width; i = i + 2) 
		{
			for (int j = 0; j < height; j = j + 2) 
			{
				Color pixelcolor= new Color(image.getRGB(i,j));
				float r=pixelcolor.getRed();
				float gb=pixelcolor.getGreen();
				float b=pixelcolor.getBlue();	
				int gloup = 0;
				for(int x = 0; x<kernel.getHeight();x++){
					for(int y = 0; y<kernel.getWidth();y++){
						if(i+x<width && j+y<height){
							int x_index = i + x - kernel.getWidth()/2;
							int y_index = j + y - kernel.getHeight()/2;
							
							r=r+ r*data[gloup];
							gb=gb+ gb*data[gloup];
							b=b+ b*data[gloup];	
							
							gloup++;
							
					    }
					}
				}
				rgb=new Color((int)r/9,(int)gb/9,(int)b/9).getRGB();	
				img.setRGB(i, j, rgb);
			}
		}
		return img;
	}
	
	public static Image readBMP(String filename) {
		return readBMP(new File(filename));
	}
}
