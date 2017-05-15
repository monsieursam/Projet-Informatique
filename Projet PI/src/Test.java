import traitementImage.*;

public class Test {
	public static void main(String[] args) {
		System.out.println("Testing the construction of a tree from an image");

		
		// Second test with a real image
		reconstructImage(new BinaryImage("Example256.png"));
	}

	static void reconstructImage(BinaryImage img) {
		// Construct a new Quad-tree from an image
		QTree treeFromImage = Compression.QTreeFromImage(img, 0, 0, img.getSize());
		BinaryImage reconstructedImage = new BinaryImage(img.getSize());
		Compression.imgFromQTree(treeFromImage, reconstructedImage, 0, 0, img.getSize());

		//visualize the result
		new ImageViewer(reconstructedImage);

	}
}
