package traitementImage;
public class QTree{
	private int color; // décrit la couleur associée à la racine : 0 pour noir, 1 pour blanc et -1 si la racine est un noeud interne (donc sans couleur).	
	private QTree[] children; //contient les références vers les enfants de la racine (ce champ sera null si la racine est déjà une feuille).

	//construit une feuille (pas d'enfant) et initialise le champ color,
	public QTree(int colorx) {
		this.color=colorx ; 
	}
	
	//construit un sommet interne (initialise le champ children pour stocker quatre enfants 
	public QTree(QTree topLeft, QTree topRight, QTree bottomLeft, QTree bottomRight){
		this.color=-1; 
		this.children= new QTree[4]; 
		this.children[0]=topLeft; 
		this.children[1]=topRight; 
		this.children[2]=bottomLeft; 
		this.children[3]=bottomRight;

	}
	
	//vérifie si c'est une feuille (pas d'enfant),
	public boolean isLeaf(){ 
		if(this.children==null) {return true ; }
		else return false ; 
	}
	
	//renvoie la couleur du Quad-tree si c'est une feuille, et -1 si c'est un noeud interne,
	public int getColor() {
		return this.color ; 
	}
	
	// renvoie le index-ième enfant d'un sommet donné (index sera compris entre 0 et 3). On supposera que children est non nul.
	public QTree getChild(int index) {
		
			return this.children[index];

		
	}

}