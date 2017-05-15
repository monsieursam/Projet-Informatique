package traitementImage;


public class Noeud {
    private int color; //0 pour noir  , 1 pour blanc et -1 no color
    private Noeud fils1;
    private Noeud fils2;
    private Noeud fils3;
    private Noeud fils4;

    //constructeur
    public Noeud(){
        this.color = -1;
        this.fils1 = null;
        this.fils2 = null;
        this.fils3 = null;
        this.fils4 = null;
    }
    public Noeud(int color){
        this.color = color;
        this.fils1 = null;
        this.fils2 = null;
        this.fils3 = null;
        this.fils4 = null;
    }

    public int getColor() {
        return this.color;
    }

    public Noeud getFils1(){return this.fils1;}

    public Noeud getFils2(){return this.fils2;}

    public Noeud getFils3(){return this.fils3;}

    public Noeud getFils4(){return this.fils4;}


    public void setColor(int color) {
        this.color = color;
    }

    public void setFils1(Noeud fils1){ this.fils1 = fils1;}

    public void setFils2(Noeud fils1){ this.fils2 = fils2;}

    public void setFils3(Noeud fils1){ this.fils3 = fils3;}

    public void setFils4(Noeud fils1){ this.fils4 = fils4;}

    public static int areIdentical(int[][] tab){
        int x = tab[0][0];
        for (int i = 0; i < tab.length; i++){
            for (int j = 0; j < tab[0].length; j++){
                if (tab[i][j] != x ){
                    return -1;
                }
            }
        }
        return x;
    }
    public static Noeud tabToQuadTree(int[][] tab){
    	Noeud res = new Noeud();
        int n = tab.length / 2;
        int m = tab[0].length / 2;
        int[][] fils1 = new int[n][m] ;
        int[][] fils2 = new int[tab.length - n][m];
        int[][] fils3 = new int[n][tab[0].length -m];
        int[][] fils4 = new int[tab.length -n][tab[0].length - m] ;
        for (int i = 0; i < tab.length; i++ ){
            for (int j = 0; j < tab[0].length; j++){
                if (i < n  && j < m ){
                    fils1[i][j] = tab[i][j];
                }else{
                    if (j < m){
                        fils2[i-n-1][j] = tab[i][j];
                    }else{
                        if (i < n){
                            fils3[i][j-m-1] = tab[i][j];
                        }else {
                            fils4[i-n-1][j-m-1] =tab[i][j];
                        }
                    }
                }
            }
        }
        if (areIdentical(fils1) != -1){
            res.fils1.color = fils1[0][0];
        }else{
            res.fils1 = tabToQuadTree(fils1);
        }
        if (areIdentical(fils2) != -1){
            res.fils2.color = fils2[0][0];
        }else {
            res.fils2 = tabToQuadTree(fils2);
        }if (areIdentical(fils3) != -1){
            res.fils3.color = fils3[0][0];
        }else  {
        	res.fils3 = tabToQuadTree(fils3);
        }if (areIdentical(fils4) != -1){
            res.fils4.color = fils4[0][0];
        }else{
            res.fils4 = tabToQuadTree(fils4);
        }
        return res;
    }
}
