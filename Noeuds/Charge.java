package Noeuds;

public class Charge {

 private String idCharge;
 private double module;
 private Noeud n;
 private double pX;
 private double pY;

 public Charge(String s, Noeud n, double px, double py){
  
  this.idCharge=s;
  this.n=n;
  this.pX=px;
  this.pY=py;

 }

 public Noeud getNoeud(){
  return this.n;
 }

 public double getpX(){
  return this.pX;
 }

 public double getpY(){
  return this.pY;
 }

 public String getid(){
  return this.idCharge;
 }

 public boolean contain(Noeud n){
  boolean test = false;
  if (this.getNoeud()==n){
   test=true;
  }
  return test;
 }
 public String toStringBNF(){
  String res="";;
  res="Charge;"+String.valueOf(this.getpX())+";"+this.getpY();
  return res;
 }

}
