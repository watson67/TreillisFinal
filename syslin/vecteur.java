package syslin;

import Noeuds.*;
import treillis.*;

public class vecteur {

 //attribut
 private Noeud n1; //coefficient directeur
 private Noeud n2;
 private double abscisse;
 private double ordonnee;
 private double norme;

 //contructeur
 public vecteur(Noeud n1, Noeud n2 ){
  this.n1=n1;
  this.n2=n2;
  this.abscisse=n2.getabscisse()-n1.getabscisse();
  this.ordonnee=n2.getordonnee()-n1.getordonnee();
  this.norme = Math.sqrt((this.abscisse)*(this.abscisse)+(this.ordonnee)*(this.ordonnee));
 }

 public vecteur(double x , double y ){
 
  this.abscisse=x;
  this.ordonnee=y;
  this.norme = Math.sqrt((this.abscisse)*(this.abscisse)+(this.ordonnee)*(this.ordonnee));
 }

 //methode get
 public Noeud getn1(){
  return this.n1;
}

public Noeud getn2(){
 return this.n2;
}


public double getnorme(){
 return this.norme;
}

public double getabscisse(){
 return this.abscisse;
}

public double getordonnee(){
 return this.ordonnee;
}


}
