package Parseur;
/* Class Parseur.Joint -- for BHVParser for defining DGP instances
 *
 * original downloaded from: http://inverseprobability.com/mocap/index.html
 *
 * August 2016
 *
 * Antonio Mucherino
 *
 */

public class Joint {
    private int nombre;
    private String nom;
    private String nomCourt;
    private double x;
    private double y;
    private double z;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double motionX;
    private double motionY;
    private double motionZ;

    private Joint parents[] = {};

    //CONSTRUCTEURS



    /**
     * constructeur avec un nombre
     *
     * @param nombre creation avec un nombre
     */
    public Joint(int nombre) {
        setNombre(nombre);
    }


    /**
     * constructeur avec un onmbre et un nom
     *
     * @param nombre creation avec un nombre
     * @param nom creation avec un nom
     */
    public Joint(int nombre, String nom) {
        setNombre(nombre);
        setNom(nom);
    }


    //METHODE

    /**
     * methode de conversion de nom
     *
     * @return String le nom convertit
     */
    public String convertName() {
        String resultat  = "UN";
        resultat = "\t" + this.getNom();
        return resultat;
    }


    // GETTERS

    /**
     * @return
     */
    public double getX() {
        return x;
    }



    /**
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * @return
     */
    public double getZ() {
        return z;
    }



    /**
     * getter
     *
     * @return nombre :int
     */
    public int getNombre() {
        return nombre;
    }



    /**
     * @return
     */
    public double getOffsetX() {
        return offsetX;
    }



    /**
     * @return
     */
    public double getOffsetY() {
        return offsetY;
    }



    /**
     * @return
     */
    public double getOffsetZ() {
        return offsetZ;
    }



    /**
     * @return
     */
    public String getNom() {
        return nom;
    }



    /**
     * @return
     */
    public Joint[] getParents() {
        return parents;
    }



    /**
     * @return
     */
    public Joint getCloserParent() {
        int len = parents.length;
        if (len >= 1) return parents[len - 1];
        return null;
    }

    /**
     * @return
     */
    public double getMotionX() {
        return motionX;
    }



    /**
     * @return
     */
    public double getMotionY() {
        return motionY;
    }

    ;

    /**
     * @return
     */
    public double getMotionZ() {
        return motionZ;
    }




    /**
     * @return
     */
    public String getShortName() {
        return nomCourt;
    }



    //SETTER

    /**
     * @param i
     */
    public void setNombre(int i) {
        nombre = i;
    }

    ;

    /**
     * @param f
     */
    public void setOffsetX(double f) {
        offsetX = f;
    }



    /**
     * @param f
     */
    public void setOffsetY(double f) {
        offsetY = f;
    }



    /**
     * @param f
     */
    public void setOffsetZ(double f) {
        offsetZ = f;
    }



    /**
     * @param f
     */
    public void setX(double f) {
        x = f;
    }



    /**
     * @param f
     */
    public void setY(double f) {
        y = f;
    }



    /**
     * @param f
     */
    public void setZ(double f) {
        z = f;
    }



    /**
     * @param string
     */
    public void setNom(String string) {
        nom = string;
        nomCourt = convertName();
    }




    /**
     * @param i
     */
    public void setParents(Joint[] i) {
        parents = i;
    }




    /**
     * @param f
     */
    public void setMotionX(double f) {
        motionX = f;
    }



    /**
     * @param f
     */
    public void setMotionY(double f) {
        motionY = f;
    }

    ;

    /**
     * @param f
     */
    public void setMotionZ(double f) {
        motionZ = f;
    }




};

