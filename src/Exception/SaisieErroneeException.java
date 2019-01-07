package Exception;

import java.util.logging.Logger;

public class SaisieErroneeException extends  Exception{

    public SaisieErroneeException() {
        super();
    }

    public SaisieErroneeException(String s) {

        super(s);

        //la methode de cours .global etant en deprecated je ne peux plus l utiliser
        Logger log = Logger.getGlobal();
        log.info(" Votre chaine de caractere est incorecte : " + s +"\n");


    }

}
