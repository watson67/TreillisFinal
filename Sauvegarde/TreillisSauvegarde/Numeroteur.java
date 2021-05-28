package Sauvegarde.TreillisSauvegarde;
import java.util.*;


public class Numeroteur<TO> {
  //Attributs
 private TreeMap<Integer,TO> idVersObjet;
 //ici, cette Map/Dictionnaire associe un entier à un objet
 private Map<TO,Integer> objetVersId;

 private int prochainID;

 //Constructeurs
 private Numeroteur(){
  this(0);
 }

 private Numeroteur(int prochainID){
  this.idVersObjet = new TreeMap<>();
  //TreeMap permet de trier les entier
  this.objetVersId = new HashMap<>();

 }

 public int creeID(TO obj){
  if(this.objetVersId.containsKey(obj)){
   throw new Error("objet " + obj+ "deja dans le numeroteur");
  }
  this.idVersObjet.put(this.prochainID,obj);
  this.objetVersId.put(obj, this.prochainID);
  this.prochainID++;
  return this.prochainID-1;
 }

 public boolean objExist( TO obj){
  return this.objetVersId.containsKey(obj);
 }

 public int getOuCreeID(TO obj){
  if (this.objExist(obj)){
    return this.objetVersId.get(obj);
  }else{
   return this.creeID(obj);
  }
  }
 }

