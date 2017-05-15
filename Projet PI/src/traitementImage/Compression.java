package traitementImage;

public class Compression {

	/**
	 * Set a binary image corresponding to a given Quad-tree
	 */
	public static void imgFromQTree(QTree t, BinaryImage img, int x, int y,
			int size) {
		if (t.isLeaf()) {
			if (t.getColor() == 0)
				img.fillAreaBlack(x, y, size);
			else
				img.fillAreaWhite(x, y, size);
		} else {
			int s = size / 2;
			imgFromQTree(t.getChild(0), img, x, y, s);
			imgFromQTree(t.getChild(1), img, x + s, y, s);
			imgFromQTree(t.getChild(2), img, x, y + s, s);
			imgFromQTree(t.getChild(3), img, x + s, y + s, s);
		}
	}

	/**
	 * Return the Quad-tree representing an image
	 */
	public static QTree QTreeFromImage(BinaryImage img, int x, int y, int size) {
		if (img.isConstantColor(x, y, size)) {
			if (img.isBlack(x, y))
				return new QTree(0);
			else
				return new QTree(1);
		} else {
			int s = size / 2;
			return new QTree(QTreeFromImage(img, x, y, s), QTreeFromImage(img,
					x + s, y, s), QTreeFromImage(img, x, y + s, s),
					QTreeFromImage(img, x + s, y + s, s));
		}
	}
}
