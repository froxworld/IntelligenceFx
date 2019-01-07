package Squelette;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Corps {
    Centre centre;
    //Point3D centre;
    List<Os> listeFils = new ArrayList<Os>();

    /**
     * Constructeur de la classe corps avec la definition du centre
     * @param centre Points3D
     * @param listeFils Liste<Os>
     */
    public Corps(Centre centre, List<Os> listeFils) {
        this.centre = centre;
        this.listeFils = listeFils;
    }

    /**
     * Constructeur de la classe corps sans la definition du centre
     * @param listeFils
     */
    public Corps(List<Os> listeFils) {
        this.centre = new Centre(0,0,0,0,0,0);
        this.listeFils = listeFils;
    }


    /**
     * methode d ajout de fils
     * @param fils
     */
    public void ajouteFils(Os fils){
        this.listeFils.add(fils);
        fils.ajouteParent(this);
    }

    public void afficheListeFils(){
        if (listeFils!= null) {
            for (Os os : listeFils
            ) {
                System.out.println("nom" + os.nom + "taille" + os.taille);

            }
        }
    }
}
