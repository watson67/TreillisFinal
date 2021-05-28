package syslin;
import treillis.*;
import Noeuds.*;
import syslin.droite;
import java.util.*;

public abstract class calcul {
 

 

 public static double anglet(Barre b1, SegmentT s1){
  
  
  double angle=0;
  Noeud bn1=b1.getNoeud(0);
  Noeud bn2=b1.getNoeud(1);
  

  droite d= new droite(bn1,bn2);
 
  droite planX = new droite(0,d.getordonnee());
 
  double a= d.getcoeff();
  double b =planX.getcoeff();
  angle=Math.atan(Math.abs((a-b)/1+a*b));

  return angle;

 }






}
