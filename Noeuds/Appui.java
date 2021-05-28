package Noeuds;

import java.io.IOException;
import java.io.Writer;

import Sauvegarde.NumeroteurTreillis;

public abstract class Appui extends Noeud {

 //Attributs

 private double position;

 //constructeur

Appui(int i, double p){
 super(i);
 this.position=p;
}
Appui(){
}
public double getposition(){
 return this.position;
}
  public void setposition(double a){
   this.position=a;
}
public abstract void save(Writer w, NumeroteurTreillis<Noeud> n) throws IOException;
        
    

 
}
