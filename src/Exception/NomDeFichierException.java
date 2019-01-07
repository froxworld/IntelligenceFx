package Exception;

import java.util.logging.Logger;

public class NomDeFichierException extends Exception {
    public NomDeFichierException() {
        super();
    }

    public NomDeFichierException(String s) {

        super(s);

        //la methode de cours .global etant en deprecated je ne peux plus l utiliser
        Logger log = Logger.getGlobal();
        log.info(" Votre nom de fichier  : " + s +"\n");


    }
}
