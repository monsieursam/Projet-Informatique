package Messagerie;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;


import java.io.OutputStream;
import java.nio.ByteBuffer;

public class envoie {

    public static void envoie(BufferedImage image)  {
    	try{
	        Socket socket = new Socket("localhost", 13085);
	        OutputStream outputStream = socket.getOutputStream();
	
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        ImageIO.write(image, "jpg", byteArrayOutputStream);
	
	        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
	        outputStream.write(size);
	        outputStream.write(byteArrayOutputStream.toByteArray());
	        outputStream.flush();
	        System.out.println("Flushed: " + System.currentTimeMillis());
	
	        Thread.sleep(120000);
	        System.out.println("Closing: " + System.currentTimeMillis());
	        socket.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
}

