/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syslin;

import static syslin.Matrice.afficheTableau;
import static syslin.Matrice.aleaUnOuDeux;
import static syslin.Matrice.creeVecteur;
import static syslin.Matrice.identite;
import static syslin.Matrice.matAleaZeroUnDeux;
import static syslin.Matrice.matTest1;
import static syslin.Matrice.matTest2;

/**
 *
 * @author thiba
 */
public class Syslin {
    
    public static void main(String[] args){
          int nl=2, nc=3;
        Matrice m = new Matrice(nl,nc);
        //Affiche du tableau des coefficients
        for (int i=0; i< nl; i++){
            for (int j=0; j< nc; j++){
                System.out.print(m.coeffs[i][j]+" ");
            }
            System.out.println();
    }
        
    //Affiche du tableau des coefficients de la matrice identité
    System.out.println("Entrer la dimension de la matrice identité n");
    int n=Lire.i();
    Matrice mi= identite(n);
    for ( int i=0; i<n;i++){
        for (int j=0; j< n; j++){
            System.out.print(mi.coeffs[i][j]+" ");
        }
        System.out.println();
    }
    //Appel de la méthode matTest1
    System.out.println("Entrer la dimension de la matrice matTest1, n");
    n=Lire.i();
    Matrice m1=matTest1(n);
    for(int i=0; i<n; i++){
        for(int j=0;j<n;j++){
            System.out.print(m1.coeffs[i][j]+" ");
        }
        System.out.println();
    }
    //Appel de la méthode matTest2
    System.out.println("Entrer la dimension de la matrice matTest2, n");
    n=Lire.i();
    Matrice m2=matTest2(n);
    for(int i=0; i<n; i++){
        for(int j=0;j<n;j++){
            System.out.print(m2.coeffs[i][j]+" ");
        }
        System.out.println();
    }
    
    //Appel de la méthode aleaUnOuDeux
    System.out.println("entrer le nombre de ligne de la matrice aleaUnOuDeux, nl = ");
    nl=Lire.i();
    System.out.println("entrer le nombre de colonne de la matrice aleaUnOuDeux, nc = ");
    nc=Lire.i();
    Matrice m3=aleaUnOuDeux(nl,nc);
     for(int i=0; i<nl; i++){
        for(int j=0;j<nc;j++){
            System.out.print(m3.coeffs[i][j]+" ");
        }
        System.out.println();
    }
    
    //Appel de la méthode intAlea
    System.out.println("Entrer un nombre pz, compris entre 0 et 1");
    double pz=Lire.d();
    System.out.println("entrer nl");
    nl=Lire.i();
    System.out.println("entrer nc");
    nc=Lire.i();
    Matrice m4 = matAleaZeroUnDeux(nl, nc, pz);
    
    for(int i=0; i<nl; i++){
        for(int j=0;j<nc;j++){
            System.out.print(m3.coeffs[i][j]+" ");
        }
        System.out.println();
    }
    System.out.println("Entrer le nombre de coordonnées du vecteur");
    nc=Lire.i();
    double[]vec = new double[nc];
    System.out.println("Remplissage du vecteur");
    for(int i=0; i <nc; i++){
        vec[i]=Lire.d();
    }
    
    Matrice vecteur = creeVecteur(vec);
    afficheTableau(vecteur, 1, nc);
    
    
}
}
