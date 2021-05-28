
 package Sauvegarde;
 import java.util.*;
 
 
 public class NumeroteurTreillis<TO> {
   
  //Attributs
  private TreeMap<Integer,TO> idVersObjet;
  //ici, cette Map/Dictionnaire associe un entier à un objet
  private Map<TO,Integer> objetVersId;
 //ici, cette Map/Dictionnaire associe un objet à un id

 /*Le numéroteur fonctionne comme un dictionnaire, sauf qu'il fonctionne dans les deux sens :
 on peut trouver la définition d'un mot en cherchant se dernier, mais dans notre cas, on peut aussi trouver
 le mot à partir de la définition. Ainsi, on peut trouver un objet à partir de son id, mais aussi
 trouver l'id à partir de l'objet*/
 
  private int prochainID;
 
  //Constructeurs
  public NumeroteurTreillis(){
    //Constuit un numeroteur vide
   this(0);
  }
 
  public NumeroteurTreillis(int prochainID){
  
    this.prochainID=prochainID;
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

 //méthode qui renvoie l'id d'un objet à partir d'un objet
  public int getid(TO obj){
   if (this.contain(obj)){
     return this.objetVersId.get(obj);
   }else{
    throw new Error("Objet"+ obj +" inconnu dans numéroteur");
   }
   }
   
   //méthode qui renvoie ou crée un id si l'objet n'est pas contenue dans le numeroteur
   public int getOuCreeID(TO obj){
     if (this.contain(obj)){
       return this.objetVersId.get(obj);
     }else{
      return this.creeID(obj);
     }
     }
     
     //méthode qui permet de savoir si un objet est contenue dans le numéroteur, à partir de son id
     public boolean idExist(int id){
       return this.idVersObjet.containsKey(id);
     }
     
     //méthode qui renvoie un objet, à partir de son id
     public TO getObj(int id){
       if(this.idExist(id)){
         throw new Error ("identificateur n'existe pas");
       }
       return this.idVersObjet.get(id);
     }
 
     //méthode qui associe un objet à un id
     public void associe(int id, TO obj){
       if(this.idExist(id)){
         throw new Error ("identificateur existant");
       }
       this.idVersObjet.put(id,obj);
       this.objetVersId.put(obj,id);
     }
  }
 
 