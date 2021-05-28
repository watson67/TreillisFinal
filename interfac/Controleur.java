/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Noeuds.AppuiDouble;
import Noeuds.AppuiSimple;
import Noeuds.Charge;
import Noeuds.Noeud;
import Noeuds.NoeudSimple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import treillis.*;


/**
 *
 * @author lemei
 */
public class Controleur {

    private MainPanel vue;

    private int IDnoeud=0;
    private int IDBarre=0;
    
    private int etat;

    private double[] pos1 = new double[2];

    private List<Figure> selection;

    private List<Figure> triangle;

    private List<typeBarre> typeBarre = new ArrayList<typeBarre>();;

    private List<TriangleTerrain> lt= new ArrayList<TriangleTerrain>();
    
    private Terrain terrain = new Terrain();

    private List<Barre> lb = new ArrayList<Barre>();

    private List<Noeud> ln =new ArrayList<Noeud>();;

    private Treillis treillis = new Treillis();

    private File inter;

    private File CreaTreillis;



    public Treillis getTreillis(){

        return this.treillis;

    }

    public Controleur(MainPanel vue) {
        this.vue = vue;
        this.selection = new ArrayList<>();
        

        try {
  
            // Recevoir le fichier 
            File f = new File("creaTreillis.txt");
  
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile()){
                System.out.println("Fichier créé");
                this.CreaTreillis=f;}
            else{
                System.out.println("Fichier existe deja");
                f.delete();
                f.createNewFile();
                this.CreaTreillis=f;
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        
   

        try {
  
            // Recevoir le fichier 
            File f = new File("inter.txt");
  
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile()){
                System.out.println("Fichier créé");
                this.inter=f;}
            else{
                System.out.println("Fichier existe deja");
                f.delete();
                f.createNewFile();
                this.inter=f;
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }

    }

    public String retourCrea() throws IOException {

        String res ="";
        try (BufferedReader bin = Extraire("creaTreillis")){
            String line;
    
            while((line = bin.readLine())!=null && line.length() != 0){
             res=res+line+"\n";

    }
        
    }
    return res;
}
public String retourinter() throws IOException {

    String res ="";
    try (BufferedReader bin = Extraire("inter")){
        String line;

        while((line = bin.readLine())!=null && line.length() != 0){
         res=res+line+"\n";

}
    
}
return res;
}

    
    public MainPanel getvue(){
       return this.vue;
    }

    public void setvue(MainPanel p){
        this.vue=p;
     }
    
    public void changeEtat(int nouvelEtat) {
        if (nouvelEtat == 20) {
            this.vue.getRbSelect().setSelected(true);
            this.selection.clear();
            this.vue.redrawAll();
        } else if (nouvelEtat == 30) {
            // creation de points
            this.vue.getRbPoints().setSelected(true);
            this.selection.clear();
            this.vue.getbGrouper().setDisable(true);
            this.vue.redrawAll();
        } else if (nouvelEtat == 40) {
            // creation de segments étape 1
            this.vue.getRbSegments().setSelected(true);
            this.selection.clear();
            this.vue.getbGrouper().setDisable(true);
            this.vue.redrawAll();
        } else if (nouvelEtat == 41) {
            // creation de segments étape 2
        }
        this.etat = nouvelEtat;
    }

    void clicDansZoneDessin(MouseEvent t) {
        if (this.etat == 20) {
            Point pclic = new Point(t.getX(), t.getY());
            // pas de limite de distance entre le clic et l'objet selectionné
            Figure proche = this.vue.getModel().plusProche(pclic, Double.MAX_VALUE);
            // il faut tout de même prévoir le cas ou le groupe est vide
            // donc pas de plus proche
            if (proche != null) {
                if (t.isShiftDown()) {
                    this.selection.add(proche);
                } else if (t.isControlDown()) {
                    if (this.selection.contains(proche)) {
                        this.selection.remove(proche);
                    } else {
                        this.selection.add(proche);
                    }
                } else {
                    this.selection.clear();
                    this.selection.add(proche);
                }
                this.activeBoutonsSuivantSelection();
                this.vue.redrawAll();
            }
        } else if (this.etat == 30) {
            double px = t.getX();
            double py = t.getY();
            Color col = this.vue.getCpCouleur().getValue();
            Groupe model = this.vue.getModel();
            model.add(new Point(px, py, col));
            this.vue.redrawAll();
        } else if (this.etat == 40) {
            this.pos1[0] = t.getX();
            this.pos1[1] = t.getY();
            this.changeEtat(41);
        } else if (this.etat == 41) {
            double px2 = t.getX();
            double py2 = t.getY();
            Color col = this.vue.getCpCouleur().getValue();
            this.vue.getModel().add(
                    new Segment(new Point(this.pos1[0], this.pos1[1]),
                            new Point(px2, py2), col));
            this.vue.redrawAll();
            this.changeEtat(40);
        }
    }

    void boutonSelect(ActionEvent t) {
        this.changeEtat(20);
    }

    void boutonPoints(ActionEvent t) {
        this.changeEtat(30);
    }

    void boutonSegments(ActionEvent t) {
        this.changeEtat(40);
    }
    
    
    private void activeBoutonsSuivantSelection() {
        if (this.selection.size() < 2) {
            this.vue.getbGrouper().setDisable(true);
        } else {
            this.vue.getbGrouper().setDisable(false);
        }
    }

    /**
     * @return the selection
     */
    public List<Figure> getSelection() {
        return selection;
    }

    void boutonGrouper(ActionEvent t) {
        if (this.etat == 20 && this.selection.size() > 1) {
            // normalement le bouton est disabled dans le cas contraire
            Groupe ssGroupe = this.vue.getModel().sousGroupe(selection);
            this.selection.clear();
            this.selection.add(ssGroupe);
            this.vue.redrawAll();
        }
    }

    void changeColor(Color value) {
        if (this.etat == 20 && this.selection.size() > 0) {
            for (Figure f : this.selection) {
                f.changeCouleur(value);
            }
            this.vue.redrawAll();
        }
    }

    void realSave(File f) {
        try {
            this.vue.getModel().sauvegarde(f);
            this.vue.setCurFile(f);
            this.vue.getInStage().setTitle(f.getName());
        } catch (IOException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Problème durant la sauvegarde");
            alert.setContentText(ex.getLocalizedMessage());

            alert.showAndWait();
        } finally {
            this.changeEtat(20);
        }
    }

    void menuSave(ActionEvent t) {
        if (this.vue.getCurFile() == null) {
            this.menuSaveAs(t);
        } else {
            this.realSave(this.vue.getCurFile());
        }
    }

    void menuSaveAs(ActionEvent t) {
        FileChooser chooser = new FileChooser();
        File f = chooser.showSaveDialog(this.vue.getInStage());
        if (f != null) {
            this.realSave(f);
        }
    }

    void menuOpen(ActionEvent t) {
        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog(this.vue.getInStage());
        if (f != null) {
            try {
                Figure lue = Figure.lecture(f);
                Groupe glu = (Groupe) lue;
                Stage nouveau = new Stage();
                nouveau.setTitle(f.getName());
                Scene sc = new Scene(new MainPanel(nouveau, f, glu), 800, 600);
                nouveau.setScene(sc);
                nouveau.show();
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème durant la sauvegarde");
                alert.setContentText(ex.getLocalizedMessage());

                alert.showAndWait();
            } finally {
                this.changeEtat(20);
            }
        }
    }
//    }

    void menuNouveau(ActionEvent t) {
        Stage nouveau = new Stage();
        nouveau.setTitle("Nouveau");
        Scene sc = new Scene(new MainPanel(nouveau), 800, 600);
        nouveau.setScene(sc);
        nouveau.show();
    }

    void menuApropos(ActionEvent t) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText(null);
        alert.setContentText("Trop super ce micro-logiciel de dessin vectoriel 2D\n"
                + "réalisé par Thibault et Baptiste");

        alert.showAndWait();
    }

    
    void menuAddPoint(ActionEvent t) {
        Stage spoint=new Stage();
        spoint.setTitle("Creer Point");
        Button validation = new Button("OK");
        
        Button Appui=new Button("Appui");
        Appui.setOnAction((e)->{
            Stage sappui=new Stage(); 
            sappui.setTitle("Nouvel Appui");
            Label type=new Label("Type d'Appui");
            final TextField Ttype = new TextField();
            
            Label position=new Label("Position par rapport au sommet :");
            final TextField Tposition = new TextField();
            
            Label triangle=new Label("Triangle numero :");
            final TextField Ttriangle=new TextField();

            Label segment=new Label("Par rapport au point :");
            final TextField Tsegment = new TextField();
            
            HBox hsposition= new HBox(10,position,Tposition);
            hsposition.setAlignment(Pos.CENTER);
            
            HBox htriangle=new HBox(10,triangle,Ttriangle);
            htriangle.setAlignment(Pos.CENTER);

            HBox hssegment= new HBox(10,segment,Tsegment);
            hssegment.setAlignment(Pos.CENTER);
            
            HBox hsType= new HBox(10,type,Ttype);
            hsType.setAlignment(Pos.CENTER);
            
            Button validation1=new Button("OK");
            validation1.setAlignment(Pos.BOTTOM_RIGHT);
            VBox attributAppui=new VBox(10,hsType,htriangle,hssegment,hsposition,validation1);
            attributAppui.setAlignment(Pos.CENTER);
            
            
            
        Scene scene1=new Scene(attributAppui,400,300);
        sappui.setScene(scene1);
        sappui.show();

        

         validation1.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
                        if(Ttype.getText().equals("simple")){
                            
                            TriangleTerrain rTriangleTerrain=lt.get(Integer.parseInt(Ttriangle.getText())-1);
                            int sommet=Integer.parseInt(Tsegment.getText());
                            double a=Double.parseDouble(Tposition.getText());
                            AppuiSimple aSimple=new AppuiSimple(IDnoeud+1,rTriangleTerrain,sommet,a);
                            Point point=new Point(aSimple.getabscisse(),aSimple.getordonnee());
                            String crea="";
                            String inte="";
                            ln.add(aSimple);


                            try {
                                crea= retourCrea();
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                            try {
                                inte=retourinter();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try{
                             
                             FileWriter fw = new FileWriter(CreaTreillis);
                             BufferedWriter bw = new BufferedWriter(fw);
                              bw.write(crea+aSimple.toStringBNF());
                              bw.close();
                               }catch (IOException m){
                     m.printStackTrace();
                          }
                          try{
                            
                             FileWriter fw = new FileWriter(inter);
                             BufferedWriter bw = new BufferedWriter(fw);
                              bw.write(inte+point.toStringBNFsn());
                              bw.close();
                               }catch (IOException m){
                     m.printStackTrace();
                          }
                          
                        
                        }if(Ttype.getText().equals("double")){
                            TriangleTerrain rTriangleTerrain=lt.get(Integer.parseInt(Ttriangle.getText())-1);
                            int sommet=Integer.parseInt(Tsegment.getText());
                            double a=Double.parseDouble(Tposition.getText());
                            AppuiDouble aDouble=new AppuiDouble(IDnoeud+1,rTriangleTerrain,sommet,a);
                            Point point=new Point(aDouble.getabscisse(),aDouble.getordonnee());
                            String crea="";
                            String inte="";
                            ln.add(aDouble);
                            try {
                                crea= retourCrea();
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                            try {
                                inte=retourinter();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try{
                             
                             FileWriter fw = new FileWriter(CreaTreillis);
                             BufferedWriter bw = new BufferedWriter(fw);
                              bw.write(crea+aDouble.toStringBNF());
                              bw.close();
                               }catch (IOException m){
                     m.printStackTrace();
                          }
                          try{
                            
                             FileWriter fw = new FileWriter(inter);
                             BufferedWriter bw = new BufferedWriter(fw);
                              bw.write(inte+point.toStringBNFsn());
                              bw.close();
                               }catch (IOException m){
                     m.printStackTrace();
                          }
                          
                        
                        }
                        IDnoeud++;
            sappui.hide();   
                        }
        });
        });
        
        Button Noeud=new Button("Noeud");
        Noeud.setOnAction((e)->{
            Stage snoeud=new Stage();  
            snoeud.setTitle("Nouveau Noeud");
            Label abscisse=new Label("Abscisse :");
            final TextField Tabscisse = new TextField();
            
            Label ordonnee=new Label("Ordonnee :");
            final TextField Tordonnee = new TextField();
            
            HBox hsabscisse= new HBox(10,abscisse,Tabscisse);
            hsabscisse.setAlignment(Pos.CENTER);
            
            HBox hsordonnee= new HBox(10,ordonnee,Tordonnee);
            hsordonnee.setAlignment(Pos.CENTER);
            
            Button validation2=new Button("OK");
            
            VBox attributNoeud=new VBox(10,hsabscisse,hsordonnee,validation2);
            
            attributNoeud.setAlignment(Pos.CENTER);            
        Scene scene2=new Scene(attributNoeud,400,300);
        snoeud.setScene(scene2);
        snoeud.show();
        
        validation2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
           public void handle(ActionEvent e){
               double x=Double.parseDouble(Tabscisse.getText());
               double y=Double.parseDouble(Tordonnee.getText());
               NoeudSimple noeud = new NoeudSimple(IDnoeud+1,x,y);
               Point point = new Point(x,y);
               ln.add(noeud);
               String crea="";
               String inte="";
               
               try {
                   crea= retourCrea();
               } catch (IOException e2) {
                   // TODO Auto-generated catch block
                   e2.printStackTrace();
               }
               try {
                   inte=retourinter();
               } catch (IOException e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               try{
                
                FileWriter fw = new FileWriter(CreaTreillis);
                BufferedWriter bw = new BufferedWriter(fw);
                 bw.write(crea+noeud.toStringBNF());
                 bw.close();
                  }catch (IOException m){
        m.printStackTrace();
             }
             try{
               
                FileWriter fw = new FileWriter(inter);
                BufferedWriter bw = new BufferedWriter(fw);
                 bw.write(inte+point.toStringBNFsn());
                 bw.close();
                  }catch (IOException m){
        m.printStackTrace();
             }
               IDnoeud++;
           snoeud.hide();
                   }
       });

       });
        
       HBox choix=new HBox(10,Appui,Noeud);
       choix.setAlignment(Pos.CENTER);
       
        VBox vchoix=new VBox(50,choix,validation);
        vchoix.setAlignment(Pos.CENTER);
        
        Scene scene=new Scene(vchoix,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            spoint.hide();
                    }
        });
         
         spoint.setScene(scene);
        spoint.show();
    }
    void menuAddSegment(ActionEvent t) {
       
        Stage ssegment=new Stage();
        ssegment.setTitle("Creer une barre");
        Button validations = new Button("Valider");
        Button annuler=new Button("Annuler");
        HBox validationannulation=new HBox(annuler,validations);
        validationannulation.setAlignment(Pos.BOTTOM_CENTER);

        Label id=new Label("identificateur");
        final TextField tid = new TextField();
        tid.setPromptText("Entrer un nombre");
        HBox hsid= new HBox(10,id,tid);
        hsid.setAlignment(Pos.CENTER);

        Label tb=new Label("numéro du type de barre");
        final TextField ttb = new TextField();
        ttb.setPromptText("Entrer le numéro du type de barre");
        HBox hstb= new HBox(10,tb,ttb);
        hstb.setAlignment(Pos.CENTER);

        Label nd=new Label("numéro du premier noeud");
        final TextField tnd = new TextField();
        tnd.setPromptText("Entrer l'identificateur du noeud");
        HBox hsnd= new HBox(10,nd,tnd);
        hsnd.setAlignment(Pos.CENTER);

        Label nf=new Label("numéro du deuxième noeud");
        final TextField tnf = new TextField();
        tnf.setPromptText("Entrer le numéro du noeud 2");
        HBox hsnf= new HBox(10,nf,tnf);
        hsnf.setAlignment(Pos.CENTER);

        VBox v= new VBox(hsid,hstb,hsnd,hsnf,validationannulation);

     

       
        Scene scene=new Scene(v,400,300);
         validations.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
                        int idb=0;
                        int idtb=0;
                        int n1=0;
                        int n3=0;

                        try{

                            try{

                            idb=Integer.valueOf(tid.getText());
                            idtb=Integer.valueOf(ttb.getText());
                            n1=Integer.valueOf(tnd.getText());
                            n3=Integer.valueOf(tnf.getText());
                            System.out.println(n3);
                           
                            //on recupere maintenant les noeuds avec les id corresepondants
                            Barre b = new Barre();
                            b.setid(idb);

                            for(int i=0;i<ln.size();i++){


                                System.out.println("on est ici");
                            System.out.println(ln.get(i).toStringBNF());

                            }

                            System.out.println("sie lln "+ ln.size());
                            ArrayList<Noeud> ltemp = new ArrayList<Noeud>();
                            for(int v=0;v<ln.size();v++){
                                
                                
                                System.out.println("id liste = " +  ln.get(v).getid());
                                System.out.println("n1 = " +  n1);
                                System.out.println("n2 = " +  n3);
                               

                                if(ln.get(v).getid()==n1){

                                    System.out.println("yo");
                                    System.out.println("n1="+n1+" id noeud ="+ln.get(v).getid());
                                    ltemp.add(ln.get(v));

                                }

                                
                            }  
                            
                            for(int v=0;v<ln.size();v++){

                                System.out.println("n3="+n3+" id noeud ="+ln.get(v).getid());
                                if(ln.get(v).getid()==n3){
                                    
                                System.out.println("yo2");
                                ltemp.add(ln.get(v));

                            }   
                        }
                            for(int i=0;i<typeBarre.size();i++){
                                System.out.println("test typebarre false");
                                if(typeBarre.get(i).getid()==idtb){
                                    System.out.println("test typebarre");
                                    b.settypeBarre(typeBarre.get(i));

                                }

                                }

                                b.setNoeud1(ltemp.get(0));
                                b.setNoeud2(ltemp.get(1));

                                System.out.println(b.getNoeud(0).toStringBNF());
                                System.out.println(b.getNoeud(1).toStringBNF());
                                Point p1 = new Point(b.getNoeud(0).getabscisse(),b.getNoeud(0).getordonnee());
                                Point p2 = new Point(b.getNoeud(1).getabscisse(),b.getNoeud(1).getordonnee());
                                
                                
                                Segment s1 = new Segment (p1,p2);


                                System.out.println(s1.toStringBNFsn());

                                String crea="";
                                String inte="";


                                try {
                                    crea= retourCrea();
                                } catch (IOException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                try {

                                    inte=retourinter();


                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                try{
                                    File f = new File("creaTreillis.txt");
                                    FileWriter fw = new FileWriter(CreaTreillis.getAbsoluteFile());
                                    BufferedWriter bw = new BufferedWriter(fw);
                                     bw.write(crea+b.toStringBNF());
                                     bw.close();

                                      }catch (IOException m){
                            m.printStackTrace();
                                 }
    
                                 try{
                                    File f = new File("inter.txt");
                                    FileWriter fw = new FileWriter(f.getAbsoluteFile());
                                    BufferedWriter bw = new BufferedWriter(fw);
                                     bw.write(inte+s1.toStringBNFsn());
                                     bw.close();
                                      }catch (IOException m){
                            m.printStackTrace();
                                 }
                   
                                }catch(NumberFormatException ff){
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Erreur");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Erreur dans le programme");
                            
                                    alert.showAndWait();

                                }

                            ssegment.hide();

                            
                            
                            


                        }
                        catch (NumberFormatException err ){
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Erreur");
                            alert.setHeaderText(null);
                            alert.setContentText("Il faut rentrer des nombres");
                    
                            alert.showAndWait();
                        }
                        

            ssegment.hide();
                    }
        });
        
        
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                   public void handle(ActionEvent e){
           ssegment.hide();
                   }
       });
         ssegment.setScene(scene);
        ssegment.show();    
    }

    void menuAddGroupe(ActionEvent t) {
        Stage sgroupe=new Stage();
        sgroupe.setTitle("Creer un Groupe");
        Button validation = new Button("OK");
        VBox vsgroupe=new VBox(validation);
        vsgroupe.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene=new Scene(vsgroupe,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            sgroupe.hide();
                    }
        });
         sgroupe.setScene(scene);
        sgroupe.show();    }

        void menuAddTerrain(ActionEvent t) {
            Stage sterrain=new Stage();
            sterrain.setTitle("Définir la zone constructible");
            Button validation = new Button("OK");
            VBox vsgroupe=new VBox(validation);
            vsgroupe.setAlignment(Pos.BOTTOM_CENTER);
            Scene scene=new Scene(vsgroupe,400,300);
             validation.setOnAction(new EventHandler<ActionEvent>() {
                 @Override
                        public void handle(ActionEvent e){
                sterrain.hide();
                        }

                

            });

            Label xmin=new Label("xmin");
            final TextField txmin = new TextField();
            txmin.setPromptText("Entrer un nombre");
            
            HBox hsxmin= new HBox(10,xmin,txmin);
            hsxmin.setAlignment(Pos.CENTER);

            Label xmax=new Label("xmax");
            final TextField txmax = new TextField();
            txmax.setPromptText("Entrer un nombre");

            HBox hsxmax= new HBox(10,xmax,txmax);
            hsxmax.setAlignment(Pos.CENTER);

            Label ymin=new Label("ymin");
            final TextField tymin = new TextField();
            tymin.setPromptText("Entrer un nombre");

            HBox hsymin= new HBox(10,ymin,tymin);
            hsymin.setAlignment(Pos.CENTER);

            Label ymax=new Label("ymax");
            final TextField tymax = new TextField();
            tymax.setPromptText("Entrer un nombre");

            HBox hsymax= new HBox(10,ymax,tymax);
            hsymax.setAlignment(Pos.CENTER);

            Button validation2=new Button("Valider");
            Button annuler=new Button("Annuler");
            HBox validationannulation=new HBox(annuler,validation2);
              
  
            validationannulation.setAlignment(Pos.BOTTOM_CENTER);

            VBox attributTerrain=new VBox(10,hsxmin,hsxmax,hsymax,hsymin,validationannulation);

            validation2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                       public void handle(ActionEvent e){
                        double xmx=0;
                        double ymx=0;
                        double xmn=0;
                        double ymn=0;
                        Terrain t = new Terrain();
                        try{

                            xmx=Double.valueOf(txmax.getText());
                            ymx=Double.valueOf(tymax.getText());
                            xmn=Double.valueOf(txmin.getText());
                            ymn=Double.valueOf(tymin.getText());
                           
                            t.setmaxX(xmx);
                            t.setmaxY(ymx);
                            t.setminX(xmn);
                            t.setminY(ymn);
                            sterrain.hide();

                            
                            
                            


                        }
                        catch (NumberFormatException err ){
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Erreur");
                            alert.setHeaderText(null);
                            alert.setContentText("Il faut rentrer des nombres");
                    
                            alert.showAndWait();
                        }
              
               Stage creaTriangle=new Stage();
               creaTriangle.setTitle("Nouveau Triangle");
               Label tri=new Label("Création d'un Triangle");
               HBox hstri= new HBox(10,tri);
               hstri.setAlignment(Pos.BASELINE_CENTER);
               
               Label point1=new Label("Point 1 :");
               HBox hsp1= new HBox(10,point1);
               Label abscisse=new Label("Abscisse :");
                final TextField Tabscisse = new TextField();
                Tabscisse.setPromptText("Entrer un nombre");
                Label ordonnee=new Label("Ordonnee :");
                
                final TextField Tordonnee = new TextField();
                Tordonnee.setPromptText("Entrer un nombre");
                HBox hsabscisse= new HBox(10,hsp1, abscisse,Tabscisse,ordonnee,Tordonnee);
                 hsabscisse.setAlignment(Pos.CENTER);
            
                 Label point2=new Label("Point 2 :");
                 HBox hsp2= new HBox(10,point2);
                 Label abscisse2=new Label("Abscisse :");
                final TextField Tabscisse2 = new TextField();
                Tabscisse2.setPromptText("Entrer un nombre");
                 hsabscisse.setAlignment(Pos.CENTER);
                  Label ordonnee2=new Label("Ordonnee :");
                  final TextField Tordonnee2 = new TextField();
                  Tordonnee2.setPromptText("Entrer un nombre");
                  HBox hsabscisse2= new HBox(10,hsp2, abscisse2,Tabscisse2,ordonnee2,Tordonnee2);
                   hsabscisse2.setAlignment(Pos.CENTER);
                        
                   Label point3=new Label("Point 3 :");
                   HBox hsp3= new HBox(10,point3);
                   Label abscisse3=new Label("Abscisse :");
                    final TextField Tabscisse3 = new TextField();
                    Tabscisse3.setPromptText("Entrer un nombre");

                    Label ordonnee3=new Label("Ordonnee :");
                    final TextField Tordonnee3 = new TextField();
                    Tordonnee3.setPromptText("Entrer un nombre");
                
                    HBox hsabscisse3= new HBox(10,hsp3,abscisse3,Tabscisse3,ordonnee3,Tordonnee3);
                     hsabscisse3.setAlignment(Pos.CENTER);
                     HBox validationannulation=new HBox(annuler,validation2);
              
               VBox attributNoeud=new VBox(10,hsabscisse, hsabscisse2,hsabscisse3,validationannulation);
               
               attributNoeud.setAlignment(Pos.CENTER);  
               validationannulation.setAlignment(Pos.CENTER)     ;     
                Scene scene2=new Scene(attributNoeud, 500,400);
                
                creaTriangle.setScene(scene2);
                creaTriangle.show();
                validation2.setOnAction(new EventHandler<ActionEvent>() {
                    private PointT PointT;

                    @Override
                           public void handle(ActionEvent e){

                            double x1=0;
                            double y1=0;
                            double x2=0;
                            double y2=0;
                            double x3=0;
                            double y3=0;

                            try{
                                
                                 x1=Double.valueOf(Tabscisse.getText());
                                 y1=Double.valueOf(Tordonnee.getText());
                                
                               

                                 x2=Double.valueOf(Tabscisse2.getText());
                                 y2=Double.valueOf(Tordonnee2.getText());
                              

                                 x3=Double.valueOf(Tabscisse3.getText());
                                 y3=Double.valueOf(Tordonnee3.getText());
                               

                               
                                
                                
                                

                                
                                

                   creaTriangle.hide();
                            }catch (NumberFormatException err ){
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Erreur");
                                alert.setHeaderText(null);
                                alert.setContentText("Les abcisses et ordonnées doivent êtres de nombres et non des lettres");
                        
                                alert.showAndWait();
                            }
                            Point p3 = new Point(x3, y3);
                            Point p2 = new Point(x2, y2);
                            Point p1 = new Point(x1, y1);
                            Segment s1=new Segment(p1,p2);
                            Segment s2=new Segment(p2,p3);
                            Segment s3=new Segment(p3,p1);
                            
                            PointT pt1=new PointT(0, x1, y1);
                            PointT pt2 =new PointT(0, x2, y2);
                            PointT pt3 =new PointT(0,x3, y3);
                            TriangleTerrain tt= new TriangleTerrain(1,pt1,pt2,pt3);
                            ArrayList<TriangleTerrain> ltn=new ArrayList<TriangleTerrain>();
                            lt.add(tt);
                            ltn.add(tt);
                            t.settriangle(ltn);
                            
                           String res;
                           res="Triangle;"+"1"+"\n";
                           res=res+p1.toStringBNFsn()+"\n";
                           res=res+p2.toStringBNFsn()+"\n";
                           res=res+p3.toStringBNFsn()+"\n";
                           res=res+s1.toStringBNFsn()+"\n";
                           res=res+s2.toStringBNFsn()+"\n";
                           res=res+s3.toStringBNFsn()+"\n";
                            String crea="";
                            String inte="";
                            try {
                                crea= retourCrea();
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                            try {
                                inte=retourinter();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try{
                                File f = new File("creaTreillis.txt");
                                FileWriter fw = new FileWriter(CreaTreillis.getAbsoluteFile());
                                BufferedWriter bw = new BufferedWriter(fw);
                                 bw.write(crea+t.toStringBNF());
                                 bw.close();
                                  }catch (IOException m){
                        m.printStackTrace();
                             }

                             try{
                                File f = new File("inter.txt");
                                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                                BufferedWriter bw = new BufferedWriter(fw);
                                 bw.write(inte+res);
                                 bw.close();
                                  }catch (IOException m){
                        m.printStackTrace();
                             }
               }

               
               });
               annuler.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                       public void handle(ActionEvent e){
               creaTriangle.hide();
                       }
           });


                       }
           });
        
           annuler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                   public void handle(ActionEvent e){
           sterrain.hide();
                   }
       });

           



            Scene scenea=new Scene(attributTerrain,400,300);
            sterrain.setScene(scenea);
            sterrain.show();

            

             //sterrain.setScene(scene);
            //sterrain.show();    
    }

    
           
    
    public static Groupe Triangle(List<Figure> f) {
        Groupe res = new Groupe();
        res.set(f);

        return res;
    }

    void menuAddPoid(ActionEvent t) {
        Stage snoeud=new Stage();
        snoeud.setTitle("Creer un Poids");
        Button validation = new Button("OK");
        snoeud.setTitle("Nouveau Poids");
        
        Label abscisse=new Label("Sur le noeud numero ");
        final TextField Tabscisse = new TextField();
        
        Label ordonnee=new Label("composante en x :");
        final TextField Tordonnee = new TextField();
        
        HBox hsabscisse= new HBox(10,abscisse,Tabscisse);
        hsabscisse.setAlignment(Pos.CENTER);

        Label dordonnee=new Label("composante en y :");
        final TextField dtordonnee = new TextField();

        HBox hsordonnee= new HBox(10,ordonnee,Tordonnee);
        hsordonnee.setAlignment(Pos.CENTER);
   
        HBox hhsordonnee= new HBox(10,dordonnee,dtordonnee);
        hsordonnee.setAlignment(Pos.CENTER);

        Label did=new Label("id :");
        final TextField dtid = new TextField();

        HBox hsid= new HBox(10,did,dtid);
        hsid.setAlignment(Pos.CENTER);
        
        Button validation2=new Button("OK");
        
        VBox attributNoeud=new VBox(10,hsabscisse,hsordonnee, hhsordonnee,hsid,validation2);
        
        attributNoeud.setAlignment(Pos.CENTER);            
    Scene scene2=new Scene(attributNoeud,400,300);
    snoeud.setScene(scene2);
    snoeud.show();


       
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
                        int id=Integer.parseInt(Tordonnee.getText());

                         double x=Double.parseDouble(Tordonnee.getText());
                         double y=Double.parseDouble(dtordonnee.getText());

                         String s = dtid.getText();

                         ArrayList<Noeud> temp = new ArrayList<Noeud>();


                         for(int i=0;i<ln.size();i++){

                            if(ln.get(i).getid()==id){

                                temp.add(ln.get(i));

                            }
                         }

                         Charge c = new Charge(s, temp.get(0), x, y);
                         String crea="";
                        String inte="";
               
               try {
                   crea= retourCrea();
               } catch (IOException e2) {
                   // TODO Auto-generated catch block
                   e2.printStackTrace();
               }
               try {
                   inte=retourinter();
               } catch (IOException e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               try{
                
                FileWriter fw = new FileWriter(CreaTreillis);
                BufferedWriter bw = new BufferedWriter(fw);
                 bw.write(crea+c.toStringBNF());
                 bw.close();
                  }catch (IOException m){
        m.printStackTrace();
             }
            /* try{
               
                FileWriter fw = new FileWriter(inter);
                BufferedWriter bw = new BufferedWriter(fw);
                 bw.write(inte+point.toStringBNFsn());
                 bw.close();
                  }catch (IOException m){
        m.printStackTrace();
             }

            snoeud.hide();
                    }*/
                    }
        });
            
                 }

        void menuAddCatalogue(ActionEvent t) {


            Stage spoid=new Stage();
            spoid.setTitle("Creer un Catalogue");
            Button validation = new Button("Valider");
            VBox vspoid=new VBox(validation);
            vspoid.setAlignment(Pos.BOTTOM_CENTER);


            Label id=new Label("identificateur");
            final TextField tid = new TextField();
            tid.setPromptText("Entrer un nombre");
            
            HBox hsid= new HBox(10,id,tid);
            hsid.setAlignment(Pos.BASELINE_LEFT);

            Label cout=new Label("Coût");
            final TextField tcout = new TextField();
            tcout.setPromptText("Entrer un nombre");
            Label unitecout=new Label(" Euro");


            HBox hscout= new HBox(10,cout,tcout,unitecout);
            hscout.setAlignment(Pos.BASELINE_LEFT);

            Label lgmin=new Label("Longueur minimum");
            final TextField tlgmin = new TextField();
            tlgmin.setPromptText("Entrer un nombre");
            Label unitelg=new Label("mètres");

            HBox hslgmin= new HBox(10,lgmin,tlgmin,unitelg);
            hslgmin.setAlignment(Pos.BASELINE_LEFT);

            Label lgmax=new Label("Longueur maximum");
            final TextField tlgmax = new TextField();
            tlgmax.setPromptText("Entrer un nombre");

            HBox hslgmax= new HBox(10,lgmax,tlgmax,unitelg);
            hslgmax.setAlignment(Pos.BASELINE_LEFT);

            Label rstt=new Label("Résistance à la tension");
            final TextField trstt = new TextField();
            trstt.setPromptText("Entrer un nombre");
            Label uniters=new Label("Newton");

            HBox hsrstt= new HBox(10,rstt,trstt,uniters);
            hsrstt.setAlignment(Pos.BASELINE_LEFT);

            Label rstc=new Label("Résistance à la compression");
            final TextField trsc = new TextField();
            trsc.setPromptText("Entrer un nombre");
          

            HBox hsymax= new HBox(10,rstc,trsc,uniters);
            hsymax.setAlignment(Pos.BASELINE_LEFT);

            Button validation2=new Button("Valider");
            Button annuler=new Button("Annuler");
            HBox validationannulation=new HBox(annuler,validation2);
              
  
            validationannulation.setAlignment(Pos.BOTTOM_CENTER);

            VBox attributbarre=new VBox(10,hsid,hscout,hslgmin,hslgmax,hsrstt,hsymax,validationannulation);

            Scene scene=new Scene(attributbarre,400,300);

             validation2.setOnAction(new EventHandler<ActionEvent>() {
                 @Override
                        public void handle(ActionEvent e){

                       int id=0 ; //identificateur
                        double cout=0; //coût au mètre
                        double longmax=0; // une longueur maximale
                        double longmin=0; //  une longueur minimale
                        double resistsn=0; //  une résistance maximale à la tension
                         double resistcmpn=0; //

                            try{
                                
                                id=Integer.valueOf(tid.getText());
                                 cout=Double.valueOf(tcout.getText());
                                
                               

                                 longmax=Double.valueOf(tlgmax.getText());
                                 longmin=Double.valueOf(tlgmin.getText());
                              

                                 resistsn=Double.valueOf(trstt.getText());
                                 resistcmpn=Double.valueOf(trsc.getText());

                                 typeBarre type= new typeBarre(id, cout, longmax, longmin, resistsn, resistcmpn);
                                typeBarre.add(type);
                                 String res;
                           
                            String crea="";
                            
                            try {
                                crea= retourCrea();
                            } catch (IOException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                            
                            try{
                                File f = new File("creaTreillis.txt");
                                FileWriter fw = new FileWriter(CreaTreillis.getAbsoluteFile());
                                BufferedWriter bw = new BufferedWriter(fw);
                                 bw.write(crea+type.toStringBNF());
                                 bw.close();
                                  }catch (IOException m){
                        m.printStackTrace();
                             }

                             
                            }catch (NumberFormatException err ){
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Erreur");
                                alert.setHeaderText(null);
                                alert.setContentText("Les valeurs rentrées ne sont pas valides");
                        
                                alert.showAndWait();
                            }
                spoid.hide();
                        }
            });
            annuler.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                       public void handle(ActionEvent e){
               spoid.hide();
                       }
           });
             spoid.setScene(scene);
            spoid.show();    }

    void menuModifPoint(ActionEvent t) {
        Stage mpoint=new Stage();
        mpoint.setTitle("Modifier le Point");
        Button validation = new Button("OK");
        VBox vmpoint=new VBox(validation);
        vmpoint.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene=new Scene(vmpoint,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            mpoint.hide();
                    }
        });
    }

    public static BufferedReader Extraire(String txt) throws IOException {
			
		//fonction qui extrait le fichier .txt
		 txt=txt+".txt";
		 InputStream ips = new FileInputStream(txt);
		 InputStreamReader ipsr = new InputStreamReader(ips);
		 BufferedReader br = new BufferedReader(ipsr); 

		 return br; 
				   }


        void menuAddTriangle(ActionEvent t) throws IOException {


            Stage sgroupe=new Stage();
            sgroupe.setTitle("Ajouter un triangle");
            ArrayList<Point> lp = new ArrayList<Point>();

            

            Button validation = new Button("Valider");
            Button annulation = new Button("Annuler");

            VBox vsgroupe=new VBox(annulation,validation);
            vsgroupe.setAlignment(Pos.BOTTOM_CENTER);
             ArrayList<Figure> lf= new ArrayList<Figure>();
             ArrayList<Integer> lid= new ArrayList<Integer>();
            try (BufferedReader bin = Extraire("inter")){
                String line;
        
                while((line = bin.readLine())!=null && line.length() != 0){
                 String[] bouts = line.split("[;()]");
                    int id=1;
                 if(bouts[1].equals("Triangle")) {
                    id=(Integer.parseInt(bouts[2]));
                    lid.add(id);
                 }

                if(bouts[0].equals("Segment")){
                 
                    Segment s = new Segment();
                 
                if(bouts[1].equals("Point")){
                Point p = new Point();
                p.setPx(Double.parseDouble(bouts[2]));
                p.setPy(Double.parseDouble(bouts[3]));
                s.setDebut(p);
                if (lp.contains(p)){

                }else{
                lp.add(p);
                }

    } if(bouts[4].equals("Point")){
        Point p = new Point();
        p.setPx(Double.parseDouble(bouts[5]));
        p.setPy(Double.parseDouble(bouts[6]));
        s.setFin(p);
        if (lp.contains(p)){

        }else{
        lp.add(p);
        }
    
    } 

    final TextField idtr = new TextField();
    Label nt= new Label("Triangle");
    idtr.setPromptText("Entrer l'identificateur du triangle");
    

    HBox traingle= new HBox(nt, idtr);
    VBox vv= new VBox(traingle, vsgroupe);
    Scene scene2=new Scene(vv, 500,400);
                
                sgroupe.setScene(scene2);
                sgroupe.show();

    ArrayList<RadioButton> lr= new ArrayList<RadioButton>();
    ArrayList<Label> ll= new ArrayList<Label>();
    ArrayList<HBox> hbx= new ArrayList<HBox>();

        for(int i=0; i<lp.size();i++){
            int j=0;
            Point p = lp.get(i);
            RadioButton b = new RadioButton("Point");
            Label lll = new Label("abscisse "+ String.valueOf(p.getPx())+" ordonnée "+String.valueOf(p.getPy()));
            HBox bx = new HBox(b,lll);
            hbx.add(bx);
        }
        //Scene scene = new Scene(hbx,400,300);

    
       
        
    }
    
            }
            
    }catch (NumberFormatException err ){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Il faut rentrer des nombres");
    
        alert.showAndWait();
    }
    
    
        }
    
              
    
         

    void menuModifSegment(ActionEvent t) {
        Stage msegment=new Stage();
        msegment.setTitle("Modifier le Segment");
        Button validation = new Button("OK");
        VBox vmsegment=new VBox(validation);
        vmsegment.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene=new Scene(vmsegment,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            msegment.hide();
                    }
        });
         msegment.setScene(scene);
        msegment.show();    }

    void menuModifPoid(ActionEvent t) {
        Stage mpoid=new Stage();
        mpoid.setTitle("Modifier le Poid");
        Button validation = new Button("OK");
        VBox vmpoid=new VBox(validation);
        vmpoid.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene=new Scene(vmpoid,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            mpoid.hide();
                    }
        });
         mpoid.setScene(scene);
        mpoid.show();    }

    void menuModifGroupe(ActionEvent t) {
        Stage mgroupe=new Stage();
        mgroupe.setTitle("Modifier le Groupe");
        Button validation = new Button("OK");
        VBox vmgroupe=new VBox(validation);
        vmgroupe.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene=new Scene(vmgroupe,400,300);
         validation.setOnAction(new EventHandler<ActionEvent>() {
             @Override
                    public void handle(ActionEvent e){
            mgroupe.hide();
                    }
        });
         mgroupe.setScene(scene);
        mgroupe.show();
    }

    

}
