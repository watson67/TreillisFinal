package Noeuds;

import java.io.IOException;
import java.io.Writer;
import Sauvegarde.NumeroteurTreillis;
import syslin.droite;
import syslin.vecteur;
import treillis.SegmentT;

public abstract class Noeud {
    // On définit cette classe comme étant abstraite car un point appartient obligatoirement à une sous-classe de la classe point
    //Attributs
    private int id; 
    private double pX;
    private double pY;
  
    //Constructeur
    Noeud(int id)
    {
       
        this.id= id;
        this.pX=0;
        this.pY=0;
        
    }

    Noeud()
    {
       
     
        
    }

    Noeud(int id,double x, double y)
    {
       
        this.id= id;
        this.pX=x;
        this.pX=y;
    }
    
   //méthode get
    public int getid(){
    return this.id;
    
    }
    public double getpx(){
        return this.pX;
    }

    public double getpy(){
        return this.pY;
    }

    
    //méthode set
    public void setid(int i){
        this.id=i;
    }



    public void setpx(double x){
        this.pX=x;
    }

    public void setpy(double x){
        this.pY=x;
    }

    public boolean possedeforce(){
        boolean test;
        if(this.getpy()==0 &&this.getpx()==0){
            test=false;
        }else{
            test=true;
        }
        return test;
    }
   public abstract double getabscisse();

   public abstract double getordonnee();

  public abstract SegmentT getsegment();

 

    //Méthode toString
    public String toString(){
        String res="";
        res="identifiant = "+String.valueOf(this.getid());
        return res;
    }

    /*public double angle(Noeud n2){
        double angle=0;
        droite d= new droite(this, n2);
        droite planX = new droite(0,this.getordonnee());
        double b= d.getcoeff();
        double a =planX.getcoeff();
        angle=Math.atan((a-b)/1+a*b);
        if(n2.getabscisse()<this.getabscisse()){
            angle=Math.PI-angle;
        }
        return angle;
    }*/

    public double angles(Noeud n2){
        
        double angle=0;
        vecteur d= new vecteur(this, n2);
        vecteur planX = new vecteur(1,0);
        angle= Math.acos((d.getabscisse()*planX.getabscisse()+d.getordonnee()*planX.getordonnee())/
        (d.getnorme()*planX.getnorme()));
        if(n2.getabscisse()<this.getabscisse() && n2.getordonnee()>this.getordonnee()){
         
        }

        else if(n2.getabscisse()<this.getabscisse()&& n2.getordonnee()<this.getordonnee()){
            angle=-angle;
        }else if(n2.getabscisse()==this.getabscisse()&& n2.getordonnee()<this.getordonnee()){
            angle=-angle;
        }else if(n2.getabscisse()>this.getabscisse()&& n2.getordonnee()<this.getordonnee()){
            angle=-angle;
        }
        return angle;
    }

    public abstract String toStringBNF();
    
    public abstract void save(Writer w, NumeroteurTreillis<Noeud> n) throws IOException;
        
    }

   




