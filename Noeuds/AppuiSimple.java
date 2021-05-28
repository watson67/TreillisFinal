/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Noeuds;
import java.io.IOException;
import java.io.Writer;

import Sauvegarde.NumeroteurTreillis;
import treillis.PointT;
import treillis.SegmentT;
import treillis.TriangleTerrain;
/**
 *
 * @author thiba
 */
public class AppuiSimple extends Appui {

        //Attributs
        TriangleTerrain triangle;
        private int numero;
        private double position;
        private int numero2;
         private double abscisse;
         private double ordonnee;
       
    
        //Constructeur
    
        public AppuiSimple(int id, TriangleTerrain t, int s, double a ){

            super(id, s) ;
            this.triangle=t;
            this.numero=s;
            this.numero2=(s+1)%3;
            this.abscisse=a*triangle.getPoint(s).getabscisse()+(1-a)*triangle.getPoint(this.numero2).getabscisse();
            this.ordonnee=a*triangle.getPoint(s).getordonnee()+(1-a)*triangle.getPoint(this.numero2).getordonnee();
            this.position=a;
       
          }

          public AppuiSimple(){

           
          }
          
         
          @Override
       public double getabscisse(){
           return this.abscisse;
       }
       @Override
       public double getordonnee(){
           return this.ordonnee;
       }
       
          public int getidtriangle(){
            return this.triangle.getid();
        }
       public double getposition(){
           return this.position;
       }
        public int getnumero(){
           return this.numero;
       }

       public int getnumero2(){
        return this.numero2;
    }

    @Override
    public SegmentT getsegment(){

        TriangleTerrain t= this.gettriangle();
        int n1=this.getnumero();
        int n2= this.getnumero2();
        PointT p1=t.getPoint(n1);
        PointT p2=t.getPoint(n2);
        SegmentT seg = new SegmentT();
        for(int i=0;i<t.getSegment().length;i++){
            SegmentT s=t.getSegment(i);
            if(s.contains(p1) && s.contains(p2)){
                seg=s;
                if(this.getnumero()== i){

                }else{
                    seg.setPointT(0, s.getPointT(1));
                    seg.setPointT(1, s.getPointT(0));
                   

                }
                seg=s;
            }

        }
        return seg;

    }
   
       
        public TriangleTerrain gettriangle(){
            return this.triangle;
        }
        public void setposition(double a){
            this.position=a;
        }
        public void setnumero(int num){
            this.numero=num;
            this.numero2=(1+this.numero)%3;
        }
        public void settriangle(TriangleTerrain t){
            this.triangle=t;
        }        
        public String toString(){
            String res="Noeud simple numero"+String.valueOf(this.getid())+", ";
    
            return res;
    
        }

        public String toStringBNF(){
            String res="AppuiSimple;"+String.valueOf(this.getid())+";";
            res= res+String.valueOf(this.getidtriangle())+";";
            res= res+String.valueOf(this.getnumero())+";";
            res= res+String.valueOf(this.getposition())+"\n";
            return res;
    
        }

        public void save(Writer w, NumeroteurTreillis<Noeud> num) throws IOException{
            if(! num.contain(this)){
                int id = num.creeID(this);
                w.append("AppuiSimple;"+this.toStringBNF());
            }
        }
    
    }