package traitementImage;

public class ImgKernel {
	private int  width;
	private int  height;
	private float data[];
	public ImgKernel(int width, int height, float data[]){
		this.width = width;
		 this.height = height;
		 if (data.length < width * height || width < 0 || height < 0){
			 throw new IllegalArgumentException();
		 }
		 this.data = new float[width * height];
		 System.arraycopy(data, 0, this.data, 0, width * height);
		
	}
	public int getXOrigin(){
		return (width - 1) / 2;
	}
	public int getYOrigin() {
		return (height - 1) / 2;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public float[] getKernelData(float[] data) {
		if (data == null) {
			data = new float[this.data.length];
		}
		else if (data.length < this.data.length) {
			throw new IllegalArgumentException("Data array too small "+ "(should be "+this.data.length+ " but is "+data.length+" )");
		}
		System.arraycopy(this.data, 0, data, 0, this.data.length);
			return data;
	}
}
