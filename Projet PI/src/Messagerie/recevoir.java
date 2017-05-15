package Messagerie;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import java.io.InputStream;

import java.nio.ByteBuffer;

public class recevoir {

    public static BufferedImage recevoir() {
    	try {
	        ServerSocket serverSocket = new ServerSocket(13085);
	        Socket socket = serverSocket.accept();
	        InputStream inputStream = (InputStream) socket.getInputStream();
	
	        System.out.println("Reading: " + System.currentTimeMillis());
	
	        byte[] sizeAr = new byte[4];
	        inputStream.read(sizeAr);
	        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
	
	        byte[] imageAr = new byte[size];
	        inputStream.read(imageAr);
	
	        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
	
	        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
	        
	        return image;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
    }

}
