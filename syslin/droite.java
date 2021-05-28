package syslin;
import Noeuds.*;
import treillis.*;

public class droite {

 //attribut
 private double coeff; //coefficient directeur
 private double ordonnee;

 //contructeur
 public droite(double c, double o){
  this.coeff=c;
  this.ordonnee=o;
 }

 public droite(Noeud a, Noeud b){

  double x1=a.getabscisse();
  double y1=a.getordonnee();
  double x2=b.getabscisse();
  double y2=b.getordonnee();
  if(x1==x2){
   this.coeff=0;
  }else{
  this.coeff=(y2-y1)/(x2-x1);
   } //equation de droite y=ax+b donc y1=coeffx1+b d'ou b=y1-coeffx1
  this.ordonnee=y1-this.coeff*x1;
  
  
 }

 

 //methode get
 public double getcoeff(){
  return this.coeff;
}

public double getordonnee(){
 return this.ordonnee;
}
 
//methode toString

public String toString(){
 String res= "";
 res="y="+String.valueOf(this.getcoeff())+"*x+"+String.valueOf(this.getordonnee());
return res;
}

 
}
