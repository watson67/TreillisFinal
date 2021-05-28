package treillis;
import java.util.ArrayList;

import Noeuds.*;
import syslin.*;

public abstract class calcul {

 public static ArrayList<Barre> barrecontenant(Noeud n, ArrayList<Barre> b){
  ArrayList<Barre> liste= new ArrayList<Barre>();
 
  for (int i=0;i<b.size();i++){
      if(liste.contains(b.get(i))){
                }
      else if(b.get(i).contain(n)==true){
          liste.add(b.get(i));
          
      }
  }
  return liste;
 }

 

 
 
}
