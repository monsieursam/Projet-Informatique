package Messagerie;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class Send {

	public static void send(BufferedImage image, String msg) {
		try {
			// ouvre un socket
			Socket socket = new Socket("localhost", 13080);
			OutputStream outputStream = socket.getOutputStream();
			// serialise l"image
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "JPG", byteArrayOutputStream);
			byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			outputStream.write(size);
			outputStream.write(byteArrayOutputStream.toByteArray());
			//serialise le message
			if (msg == null){
				msg = "";
			}
			byte[] sizemsg = ByteBuffer.allocate(4).putInt(msg.length()).array();
			byte[] bytemsg = msg.getBytes();
			outputStream.write(sizemsg);
			outputStream.write(bytemsg, 0, msg.length());
			//envoie l'image a l'adresse ip localhost(127.0.0.1) a travers le port 13080
			outputStream.flush();
			int sentSize = ByteBuffer.wrap(size).asIntBuffer().get();
			System.out.println("Sent " + sentSize + ": " + image.getHeight() + "x" + image.getWidth());
			//ferme le socket
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

