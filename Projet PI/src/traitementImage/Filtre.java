package traitementImage;

import java.awt.Image;

public class Filtre {
	Image a;
	public Image getImage(){
		return this.a;
	}
	public Filtre(Image a){
		this.a = a;
	}
}
