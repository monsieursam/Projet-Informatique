package Messagerie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observer;

public class ReceiveRunnable extends java.util.Observable implements Runnable {
	public ReceiveRunnable(Observer win) {
		addObserver(win);
	}

	public void run() {
		try {
			while (true) {
				// Ouvre un Socket
				ServerSocket serverSocket = new ServerSocket(13080);
				Socket socket = serverSocket.accept();

				// reception de l'image
				InputStream inputStream = socket.getInputStream();
				System.out.println("Receiving");
				Thread.sleep(300);
				// désérialisation
				byte[] sizeAr = new byte[4];
				inputStream.read(sizeAr);
				int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
				byte[] imageAr = new byte[size];
				int ii = inputStream.read(imageAr);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
				System.out.println("Received " + ii + "/" + size + ": " + image.getHeight() + "x" + image.getWidth());
				// Transmission de l'image a la GUI
				sauverImage(image);
				setChanged();
				notifyObservers(image);
				socket.close();
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
    public void sauverImage(BufferedImage image) throws IOException 
	{ 
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date date = new Date();
		File nomfichier = new java.io.File("images/"+ dateFormat.format(date) + ".bmp");// ou jpg 
		ImageIO.write(image, "BMP", nomfichier);//ou JPG 
	} 
}



