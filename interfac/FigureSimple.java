/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfac;

import javafx.scene.paint.Color;

/**
 *
 * @author lemei
 */
public abstract class FigureSimple extends Figure {

    private Color couleur;

    public FigureSimple(Color couleur) {
        this.couleur = couleur;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    @Override
    public void changeCouleur(Color value) {
        this.setCouleur(value);
    }
    
    public static String saveColor(Color c) {
        return c.getRed()+";"+c.getGreen()+";"+c.getBlue();
    }
    
   public static Color parseColor(String sr, String sg, String sb) {
       double rouge = Double.parseDouble(sr);
       double vert = Double.parseDouble(sg);
       double bleu = Double.parseDouble(sb);
       return Color.color(rouge, vert, bleu);
    }


}

