package Parseur;

import Squelette.Centre;
import Squelette.Corps;
import Squelette.Os;
import java.util.*;

import javafx.geometry.Point3D;
import java.lang.reflect.Array;

public class Parse {

    private String squelette;

    /**
     * Constructeur de base
     */
    public Parse() {

    }

    /**
     * Constructeur avec un nom pour le squelette
     *
     * @param squelette
     */
    public Parse(String squelette) {
        this.squelette = squelette;
    }


    public void splitChaine() {

        //pour  l instant je ne fais que sur une seule ligne mais apres je ferai une boucle qui prendra toutes les lignes

        //la ligne des coordoones de l image 1
        String image1texte = "0.308764 0.941503 -0.024308 4.082698 -0.169403 91.182754 -3.763102 0.146326 -8.330477 0.000000 15.979581 0.000000 10.827821 -11.732542 -10.615606 0.000000 5.599498 0.000000 0.000000 0.000000 0.000000 7.410968 0.907912 4.657629 0.000000 11.622666 0.000000 -14.297794 -8.318007 6.169833 0.000000 7.111289 0.000000 0.000000 0.000000 0.000000 1.428292 -0.523495 -1.483331 0.000000 16.337033 0.000000 0.000000 0.000000 0.000000 11.416418 1.285160 6.337761 65.955645 -9.943743 54.501824 0.000000 -30.482750 0.000000 4.857764 16.776436 -38.239834 0.000000 0.000000 0.000000 -12.685247 1.035279 -4.589327 -62.325948 -4.675770 -49.958363 0.000000 -30.686041 0.000000 -4.196369 13.659111 28.837873 0.000000 0.000000 0.000000 0.000000 0.000000 0.000000 0.386473 3.295756 2.536524 0.000000 0.000000 0.000000 \n";
        //String[] listeCoordonnes = new String[1000];

        //tableau cle valeur pour garder la liste du squelette
        Map<String, Triple> listeSquelette = new HashMap<>();
        int[] tableau = {6, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};

        //creation du tableaux des valeurs en splittant la chaine
        String[] tableauValeur = image1texte.split(" ");

        listeSquelette.put("centre", new Triple((Float.parseFloat(tableauValeur[0])), (Float.parseFloat(tableauValeur[1])), (Float.parseFloat(tableauValeur[2]))));
        listeSquelette.put("centreRotation", new Triple((Float.parseFloat(tableauValeur[3])), (Float.parseFloat(tableauValeur[4])), (Float.parseFloat(tableauValeur[5]))));


        listeSquelette.put("hancheDroite", new Triple((Float.parseFloat(tableauValeur[6])), (Float.parseFloat(tableauValeur[7])), (Float.parseFloat(tableauValeur[8]))));
        listeSquelette.put("genouDroit", new Triple((Float.parseFloat(tableauValeur[9])), (Float.parseFloat(tableauValeur[10])), (Float.parseFloat(tableauValeur[11]))));
        listeSquelette.put("chevilleDroite", new Triple((Float.parseFloat(tableauValeur[12])), (Float.parseFloat(tableauValeur[13])), (Float.parseFloat(tableauValeur[14]))));
        listeSquelette.put("piedDroit", new Triple((Float.parseFloat(tableauValeur[15])), (Float.parseFloat(tableauValeur[16])), (Float.parseFloat(tableauValeur[17]))));
        listeSquelette.put("finPiedDroit", new Triple((Float.parseFloat(tableauValeur[18])), (Float.parseFloat(tableauValeur[19])), (Float.parseFloat(tableauValeur[20]))));


        listeSquelette.put("hancheGauche", new Triple((Float.parseFloat(tableauValeur[21])), (Float.parseFloat(tableauValeur[22])), (Float.parseFloat(tableauValeur[23]))));
        listeSquelette.put("genouGauche", new Triple((Float.parseFloat(tableauValeur[24])), (Float.parseFloat(tableauValeur[25])), (Float.parseFloat(tableauValeur[26]))));
        listeSquelette.put("chevilleGauche", new Triple((Float.parseFloat(tableauValeur[27])), (Float.parseFloat(tableauValeur[28])), (Float.parseFloat(tableauValeur[29]))));
        listeSquelette.put("piedGauche", new Triple((Float.parseFloat(tableauValeur[30])), (Float.parseFloat(tableauValeur[31])), (Float.parseFloat(tableauValeur[32]))));
        listeSquelette.put("finPiedGauche", new Triple((Float.parseFloat(tableauValeur[33])), (Float.parseFloat(tableauValeur[34])), (Float.parseFloat(tableauValeur[35]))));

        listeSquelette.put("colonne1", new Triple((Float.parseFloat(tableauValeur[36])), (Float.parseFloat(tableauValeur[37])), (Float.parseFloat(tableauValeur[38]))));
        listeSquelette.put("colonne2", new Triple((Float.parseFloat(tableauValeur[39])), (Float.parseFloat(tableauValeur[40])), (Float.parseFloat(tableauValeur[41]))));
        listeSquelette.put("colonne3", new Triple((Float.parseFloat(tableauValeur[42])), (Float.parseFloat(tableauValeur[43])), (Float.parseFloat(tableauValeur[44]))));

        listeSquelette.put("claviculeDroite", new Triple((Float.parseFloat(tableauValeur[45])), (Float.parseFloat(tableauValeur[46])), (Float.parseFloat(tableauValeur[47]))));
        listeSquelette.put("epauleDroite", new Triple((Float.parseFloat(tableauValeur[48])), (Float.parseFloat(tableauValeur[49])), (Float.parseFloat(tableauValeur[50]))));
        listeSquelette.put("coudeDroit", new Triple((Float.parseFloat(tableauValeur[51])), (Float.parseFloat(tableauValeur[52])), (Float.parseFloat(tableauValeur[53]))));
        listeSquelette.put("poignetDroit", new Triple((Float.parseFloat(tableauValeur[54])), (Float.parseFloat(tableauValeur[55])), (Float.parseFloat(tableauValeur[56]))));
        listeSquelette.put("mainDroite", new Triple((Float.parseFloat(tableauValeur[57])), (Float.parseFloat(tableauValeur[58])), (Float.parseFloat(tableauValeur[59]))));

        listeSquelette.put("claviculeGauche", new Triple((Float.parseFloat(tableauValeur[60])), (Float.parseFloat(tableauValeur[61])), (Float.parseFloat(tableauValeur[62]))));
        listeSquelette.put("epauleGauche", new Triple((Float.parseFloat(tableauValeur[63])), (Float.parseFloat(tableauValeur[64])), (Float.parseFloat(tableauValeur[65]))));
        listeSquelette.put("coudeGauche", new Triple((Float.parseFloat(tableauValeur[66])), (Float.parseFloat(tableauValeur[67])), (Float.parseFloat(tableauValeur[68]))));
        listeSquelette.put("poigneGauche", new Triple((Float.parseFloat(tableauValeur[69])), (Float.parseFloat(tableauValeur[70])), (Float.parseFloat(tableauValeur[71]))));
        listeSquelette.put("mainGauche", new Triple((Float.parseFloat(tableauValeur[72])), (Float.parseFloat(tableauValeur[73])), (Float.parseFloat(tableauValeur[74]))));

        listeSquelette.put("cou", new Triple((Float.parseFloat(tableauValeur[75])), (Float.parseFloat(tableauValeur[76])), (Float.parseFloat(tableauValeur[77]))));
        listeSquelette.put("tete", new Triple((Float.parseFloat(tableauValeur[78])), (Float.parseFloat(tableauValeur[79])), (Float.parseFloat(tableauValeur[80]))));
        listeSquelette.put("finTete", new Triple((Float.parseFloat(tableauValeur[81])), (Float.parseFloat(tableauValeur[82])), (Float.parseFloat(tableauValeur[83]))));



        //creation du centre
        Centre centre = new Centre(0, 0, 0, 0, 0, 0);

        //creation d un liste vide
        List<Os> listeVide = new ArrayList<>();

        //creation d un nouveau corps
        Corps corps1 = new Corps(centre, listeVide);

        //
        System.out.println("tab" + tableau.length);
        int resultat = 0;
        for (int i = 0; i < tableau.length; i++) {
            resultat += tableau[i];
        }
        System.out.println("nombres de valeurs lues :" + resultat);

        /*
        for (String coordonne : image1texte.split(" ")) {
            System.out.println(coordonne);

        }*/

        for (Map.Entry<String, Triple> entry : listeSquelette.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }


    }
}
