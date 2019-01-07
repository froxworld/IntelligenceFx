package TestPersonel;

import Squelette.Centre;
import Squelette.Corps;
import Squelette.Os;
import Exception.*;
import javafx.geometry.Point3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CorpsTest {

    Corps corps1;
    Centre centre;
    Point3D pointCentre;
    List<Os> listeDesOs;
    Os colonneBas;

    public void initialisation() {

        try {
            //centre de notre humain
            pointCentre = new Point3D(0,0,0);

            //test de creation du centre du corps
            Centre centre = new Centre(1,2,3,30,30,50);

            //premieres coordonnes du fichiers Squellette.txt qui sont lapremire ligne de resultat de BHVparser
            Os colonneBas = new Os(1.428292f, -0.523495f, -1.483331f );
            colonneBas.setNom("colonneBas");


            //instanciation du corps
            corps1 = new Corps(centre,listeDesOs);

            //ajout de la collone a notre liste des Os
            //corps1.ajouteFils(colonneBas);

            //categorie3Impl.setNom("TRANSMISSION");

            corps1.afficheListeFils();


        }
        catch (Exception e) {
            //log.info(" Veuillez changer votre nom de moteur");
            e.printStackTrace();
        }
    }


    @Test
    void ajouteFils() {
        initialisation();

    }
}