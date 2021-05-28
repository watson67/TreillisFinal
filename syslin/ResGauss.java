package syslin ;

public class ResGauss {

    //Définition des attributs de la classe
    private int rang; //nombre de ligne
    private int signature; //nombre de colonne
    
    //Constructeur
    public ResGauss(int rang, int signature){
        this.rang = rang;
        this.signature = signature;
    }

    public int getrang(){
        return this.rang;
    }
    public int getsignature(){
        return this.signature;
    }

    public String toString() {
        return ("(Rang="+rang+", Signature="+signature+")");
    }
}
