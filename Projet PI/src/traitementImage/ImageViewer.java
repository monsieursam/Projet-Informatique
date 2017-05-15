package traitementImage;

import javax.swing.JFrame;

public class ImageViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;

	public ImageViewer(BinaryImage img) {
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageComponent ic = new ImageComponent(img);
		add(ic);
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}

	public ImageViewer(BinaryImage img, String name) {
		this.setTitle(name);
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageComponent ic = new ImageComponent(img);
		add(ic);
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}
}
