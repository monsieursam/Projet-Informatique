package traitementImage;


import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * traitementImage - 14/05/2017.
 */
public class ConvolutionFilter {
	private ImgKernel kernel;

	public ConvolutionFilter(ImgKernel kernel) {
		this.kernel = kernel;
	}

	public BufferedImage filterConv(BufferedImage src){
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		filterRaster(src.getRaster(), dest.getRaster());
		return dest;
	}

	public WritableRaster filterRaster(Raster src, WritableRaster dest) {
		// calculate the borders that the op can't reach...
		int kWidth = kernel.getWidth();
		int kHeight = kernel.getHeight();
		int left = kernel.getXOrigin();
		int right = Math.max(kWidth - left - 1, 0);
		int top = kernel.getYOrigin();
		int bottom = Math.max(kHeight - top - 1, 0);
		// Calculate max sample values for clipping
		int[] maxValue = src.getSampleModel().getSampleSize();
		for (int i = 0; i < maxValue.length; i++) {
			maxValue[i] = (int) Math.pow(2, maxValue[i]) - 1;
		}
		// process the region that is reachable...
		int regionW = src.getWidth() - left - right;
		int regionH = src.getHeight() - top - bottom;
		float[] kvals = kernel.getKernelData(null);
		float[] tmp = new float[kWidth * kHeight];
		for (int x = 0; x < regionW; x++) {
			for (int y = 0; y < regionH; y++) {
				for (int b = 0; b < src.getNumBands(); b++) {
					float v = 0;
					src.getSamples(x, y, kWidth, kHeight, b, tmp);
					for (int i = 0; i < tmp.length; i++)
						v += tmp[tmp.length - i - 1] * kvals[i];
					// FIXME: in the above line, I've had to reverse the order of
					// the samples array to make the tests pass.  I haven't worked
					// out why this is necessary

					// This clipping is is undocumented, but determined by testing.
					if (v > maxValue[b])
						v = maxValue[b];
					else if (v < 0)
						v = 0;
					dest.setSample(x + kernel.getXOrigin(), y + kernel.getYOrigin(), b, v);
				}
			}
		}
		// fill in the top border
		fillEdge(src, dest, 0, 0, src.getWidth(), top);
		// fill in the bottom border
		fillEdge(src, dest, 0, src.getHeight() - bottom, src.getWidth(), bottom);
		// fill in the left border
		fillEdge(src, dest, 0, top, left, regionH);
		// fill in the right border
		fillEdge(src, dest, src.getWidth() - right, top, right, regionH);
		return dest;
	}

	private void fillEdge(Raster src, WritableRaster dst, int xTopLeft, int yTopLeft, int w, int h) {
		if (w <= 0 || h <= 0) {
			return;
		}
		float[] zeros = new float[src.getNumBands() * w * h];
		dst.setPixels(xTopLeft, yTopLeft, w, h, zeros);
	}
}
