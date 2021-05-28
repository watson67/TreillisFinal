package Noeuds;
import treillis.SegmentT;
import treillis.Terrain;

import java.io.IOException;
import java.io.Writer;
import Sauvegarde.Numeroteur;
import Sauvegarde.NumeroteurTreillis;
import treillis.Treillis;

public class NoeudSimple extends Noeud {
    
    //Attributs
    
	private double abscisse;
    private double ordonnee;

    //Constructeur
    public NoeudSimple(int id, double x, double y){
        super(id);
        this.abscisse=x;
        this.ordonnee=y;
    }

    public NoeudSimple(){
        super(0);
    }

    public NoeudSimple( double x, double y){
        super(0);
        this.abscisse=x;
        this.ordonnee=y;
    }

    ///méthode get
    @Override
    public double getabscisse(){
        return this.abscisse;
    }

    @Override
    public double getordonnee(){
        return this.ordonnee;
    }

    //méthode set
    public void setAbscisse(double x){
        this.abscisse=x;
    }
    public void setOrdonnee(double y){
        this.ordonnee=y;
    }

    //méthode toString
    public String toString(){
        String res="";
        res=res+" abscisse = "+String.valueOf(this.getabscisse());
        res=res+" ordonnee = "+String.valueOf(this.getordonnee());
        return res;
    }
    
    @Override
    public  SegmentT getsegment(){
        throw new Error("Un noeud simple n'est pas lié à un segment");
    }
   

    public String toStringBNF(){
        
        String res= "NoeudSimple;";
        res=res+String.valueOf(this.getid())+";";
        res=res+"("+String.valueOf(this.getabscisse())+";"+String.valueOf(this.ordonnee)+")"+"\n";
        return res;
       }

       public void save(Writer w, NumeroteurTreillis<Noeud> num) throws IOException{
           if(! num.contain(this)){
               int id = num.creeID(this);
               w.append("NoeudSimple;"+this.toStringBNF());
           }
       }
        
    }
    

