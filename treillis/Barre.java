package treillis;
import Noeuds.Noeud;
import syslin.droite;


public class Barre {

    //Définitions des attributs
    
    private int id ; //identificateur
    private typeBarre type;
    private Noeud[] noeud;
    private double longueur;
    
    public Barre(int id, typeBarre type, Noeud debut, Noeud fin){
        
        
        this.type= type;
        this.id=id;
        this.noeud=new Noeud[2];
        this.noeud[0]=debut;
        this.noeud[1]=fin;
        double x1=debut.getabscisse();
        double y1=debut.getordonnee();
        double x2=fin.getabscisse();
        double y2=fin.getordonnee();
        this.longueur=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        
    }

    public Barre(){
        
        this.noeud= new Noeud[2];
      
        
    }

    //méthode get
    public int getid(){
        return this.id;
    }

    public typeBarre gettypeBarre(){
        return this.type;
    }

    public Noeud getNoeud(int i){
        // ! eventuellement mettre une exception si i>1
        return this.noeud[i];
        
    }  //surcharge
       public Noeud[] getNoeud(){
       
        return this.noeud;
    }

    public double getLongueur(){
       
        return this.longueur;
    }


    //méthode set
    public void setid(int i){
        this.id=i;
    }

    public void settypeBarre(typeBarre t){
        this.type= t;
    }

    public void setNoeud(int i, Noeud n){
        //! idem, exception si i>1
        this.noeud[i]=n;
        if(i==0){
            Noeud n2=this.getNoeud(1);
            double x1=n.getabscisse();
            double y1=n.getordonnee();
            double x2=n2.getabscisse();
            double y2=n2.getordonnee();
            this.longueur=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

        }else{
            Noeud n2=this.getNoeud(0);
            double x1=n.getabscisse();
            double y1=n.getordonnee();
             double x2=n2.getabscisse();
            double y2=n2.getordonnee();
            this.longueur=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

        }
     }

     public void setNoeud1(Noeud n){
  
        this.noeud[0]=n;
        
     }

     public void setNoeud2(Noeud n){
       
        this.noeud[1]=n;
        
     }

     public void rangement(Barre b1 ){

        int n1;
        int n2;
        for(int i=0;i<this.getNoeud().length;i++){

            Noeud t= this.getNoeud(i);

                for(int j=0;j<b1.getNoeud().length;j++){
                    
                    if(t == b1.getNoeud(j)){
                        n1=i;
                        n2=j;
                      
                    
                        if(i==1 && j==0){

                        }else{
                        if(i==0){
                          
                            Noeud temp = this.getNoeud(1);
                            this.setNoeud(1, this.getNoeud(0));
                            this.setNoeud(0, temp);
                        }
                        if(j==1){
                            
                            Noeud temp = b1.getNoeud(1);
                            b1.setNoeud(1, b1.getNoeud(0));
                            b1.setNoeud(0, temp);
                        }
                     
                    }
                }
                }

          
        }
        

        
    }
   

    //méthodes toString

    public String toString(){
        String res="";
        res=" identificateur = "+String.valueOf(this.getid());
        res= res+"debut = "+ this.getNoeud(0).toString()+"\n";
        res= res+ "fin = "+this.getNoeud(1).toString();
        res= res+ this.type.toString();
        return res;
    }

    public String toStringBNF(){
        String res="";
        res="Barre;"+String.valueOf(this.getid())+";";
        res=res+ String.valueOf(this.gettypeBarre().getid())+";";
        res=res+ String.valueOf(this.getNoeud(0).getid())+";";
        res=res+ String.valueOf(this.getNoeud(1).getid());
        return res;
    }

    public boolean contain(Noeud n){
        boolean test=false;
        for (int i=0;i<2;i++){
            if(this.getNoeud(i)==n){
                test=true;
            }
        }
        return test;
    }

    public Noeud getextremiteNoeud(Noeud n){
    Noeud test= this.getNoeud(0);
        for (int i=0;i<2;i++){
            if(this.getNoeud(i)!=n){
                test=this.getNoeud(i);
            }
        }
        return test;
    }
    public double angleX(){
  
           
        double angle=0;
        Noeud bn1=this.getNoeud(0);
        Noeud bn2=this.getNoeud(1);
        System.out.println("noeud 1 "+bn1.getabscisse());
        System.out.println("noeud 2 "+bn2.getabscisse());
        if(bn1.getabscisse()==bn2.getabscisse()){
            angle = Math.PI/2;
        }
        else{
        droite d= new droite(bn1,bn2);
        System.out.println(d.toString());
        droite planX = new droite(0,bn1.getordonnee());
        double a= d.getcoeff();
        double b =planX.getcoeff();
        
        angle=Math.atan((a-b)/1+a*b);
        }
        return angle;
      
       }
       public double angle(Barre n){
  
           
        double angle;
        this.rangement(n);
        Noeud bn1=this.getNoeud(0);
        Noeud bn2=this.getNoeud(1);
        Noeud bn3=n.getNoeud(0);
        Noeud bn4=n.getNoeud(1);
        droite d= new droite(bn1,bn2);
        droite d2= new droite(bn3,bn4);
        System.out.println(d.toString());
        double a= d.getcoeff();
        double b =d2.getcoeff();
        
        angle=Math.atan((a-b)/1+a*b);
        
        return angle;
      
       }

}
