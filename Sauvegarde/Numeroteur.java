package Sauvegarde;
import java.util.*;


public class Numeroteur<TO> {
  //Attributs
 private TreeMap<Integer,TO> idVersObjet;
 //ici, cette Map/Dictionnaire associe un entier à un objet
 private Map<TO,Integer> objetVersId;

 private int prochainID;

 //Constructeurs
 public Numeroteur(){
   //Constuit un numeroteur vide
  this(0);
 }

 public Numeroteur(int prochainID){

  this.idVersObjet = new TreeMap<>();
  //TreeMap permet de trier les entier
  this.objetVersId = new HashMap<>();

 }
//méthode qui permet d'ajouter un objet dans le numeroteur
 public int creeID(TO obj){

  if(this.objetVersId.containsKey(obj)){
   throw new Error("objet " + obj+ "deja dans le numeroteur");
  }
  this.idVersObjet.put(this.prochainID,obj);
  this.objetVersId.put(obj, this.prochainID);
  this.prochainID++;
  return this.prochainID-1;
 }
//méthode contain permet de savoir si le numeroteur contient un objet
 public boolean contain( TO obj){
  return this.objetVersId.containsKey(obj);
 }

 public int getid(TO obj){
  if (this.contain(obj)){
    return this.objetVersId.get(obj);
  }else{
   throw new Error("Objet"+ obj +" inconnu dans numéroteur");
  }
  }

  public int getOuCreeID(TO obj){
    if (this.contain(obj)){
      return this.objetVersId.get(obj);
    }else{
     return this.creeID(obj);
    }
    }
    public boolean idExist(int id){
      return this.idVersObjet.containsKey(id);
    }

    public TO getObj(int id){
      if(this.idExist(id)){
        throw new Error ("identificateur n'existe pas");
      }
      return this.idVersObjet.get(id);
    }


    public void associe(int id, TO obj){
      if(this.idExist(id)){
        throw new Error ("identificateur existant");
      }
      this.idVersObjet.put(id,obj);
      this.objetVersId.put(obj,id);
    }
 }

