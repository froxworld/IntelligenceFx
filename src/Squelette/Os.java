package Squelette;

import javafx.geometry.Point3D;
import javafx.util.Pair;

import java.util.List;

public class Os {
    String nom;
    List<Os> listeFils;
    Point3D origine;
    Point3D fin;
    float taille;
    boolean tailleBloque;
    boolean orgineBloque;
    boolean finBloque;
    Pair<Float,Float> limiteDeRotationX;
    Pair<Float,Float> limiteDeRotationY;
    Pair<Float,Float> limiteDeRotationZ;
    PVector pvector; // vecteur representant notre os en 3D
    float xorigine;
    float yorigine;
    float zorigine;


    public Os(String nom, Pair<Float, Float> limiteDeRotationX, Pair<Float, Float> limiteDeRotationY, Pair<Float, Float> limiteDeRotationZ) {
        this.nom = nom;
        this.orgineBloque = false;
        this.finBloque = false;
        this.tailleBloque = false;
        this.limiteDeRotationX = limiteDeRotationX;
        this.limiteDeRotationY = limiteDeRotationY;
        this.limiteDeRotationZ = limiteDeRotationZ;
    }


    public Os(Float _xorigine, Float _yorigine, Float _zorigine){

        this.xorigine =_xorigine;
        this.yorigine =_yorigine;
        this.zorigine =_zorigine;
        System.out.println("Ajout d un Os ("+ _xorigine +"," + _yorigine +","+  _zorigine +")");
    }


    public void ajouteParent(Corps parent) {

    }

    public void ajouteParent(Os parent) {
    }


    //Getter et Setter

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Os> getListeFils() {
        return listeFils;
    }

    public void setListeFils(List<Os> listeFils) {
        this.listeFils = listeFils;
    }

    public Point3D getOrigine() {
        return origine;
    }

    public void setOrigine(Point3D origine) {
        this.origine = origine;
    }

    public Point3D getFin() {
        return fin;
    }

    public void setFin(Point3D fin) {
        this.fin = fin;
    }

    public float getTaille() {
        return taille;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public boolean isTailleBloque() {
        return tailleBloque;
    }

    public void setTailleBloque(boolean tailleBloque) {
        this.tailleBloque = tailleBloque;
    }

    public boolean isOrgineBloque() {
        return orgineBloque;
    }

    public void setOrgineBloque(boolean orgineBloque) {
        this.orgineBloque = orgineBloque;
    }

    public boolean isFinBloque() {
        return finBloque;
    }

    public void setFinBloque(boolean finBloque) {
        this.finBloque = finBloque;
    }

    public Pair<Float, Float> getLimiteDeRotationX() {
        return limiteDeRotationX;
    }

    public void setLimiteDeRotationX(Pair<Float, Float> limiteDeRotationX) {
        this.limiteDeRotationX = limiteDeRotationX;
    }

    public Pair<Float, Float> getLimiteDeRotationY() {
        return limiteDeRotationY;
    }

    public void setLimiteDeRotationY(Pair<Float, Float> limiteDeRotationY) {
        this.limiteDeRotationY = limiteDeRotationY;
    }

    public Pair<Float, Float> getLimiteDeRotationZ() {
        return limiteDeRotationZ;
    }

    public void setLimiteDeRotationZ(Pair<Float, Float> limiteDeRotationZ) {
        this.limiteDeRotationZ = limiteDeRotationZ;
    }

    public PVector getPvector() {
        return pvector;
    }

    public void setPvector(PVector pvector) {
        this.pvector = pvector;
    }

}
