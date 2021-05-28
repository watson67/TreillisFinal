package treillis;
import java.util.ArrayList;
import Noeuds.Noeud;

public class listangle{
 
 private ArrayList<Noeud> listpoint; 
 private ArrayList<Double> listangle; 

public listangle(){
this.listpoint=new ArrayList<Noeud>();
this.listangle=new ArrayList<Double>();
}

public void add(Noeud n){
 this.listpoint.add(n);
}

public void add(double n){
 this.listangle.add(n);
}

public ArrayList<Double> getangle(){
 return this.listangle;
}

public ArrayList<Noeud> getnoeud(){
 return this.listpoint;
}

public Noeud getnoeud(int i){
 return this.listpoint.get(i);
}

public double getangle(int i){
 return this.listangle.get(i);
}

public void tostring(){
 for(int i=0;i<this.getangle().size();i++){
  System.out.println("angle "+ this.getangle(i)+ " avec noeud"+ this.getnoeud(i));
 }
}
}