package Squelette;

import static java.lang.Math.abs;


public class Quaternion {

    Float x = 0.0f;
    Float y = 0.0f;
    Float z = 0.0f;
    Float w = 1.0f;


    /**
     * constructeur vide
     */
    Quaternion() {
    }


    /**
     * constrcteur avec 4 paremtres x y z w
     *
     * @param x
     * @param y
     * @param z
     * @param w
     */
    Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    /**
     * Constructeur avec deja un quaternion directement
     * @param quaternion
     */
    Quaternion(Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }


    /**
     * Constructeur avec des angles
     * @param angle
     * @param pVector
     */
    Quaternion(float angle, PVector pVector) {
        set(angle, pVector);
    }


    /**
     * methode rendString
     * @return String : la chaine passée en parametres
     */
    String rendString() {
        return String.format("[ %+.2f, %+.2f, %+.2f, %+.2f ]",
                this.x, this.y, this.z, this.w);
    }


    /**
     * methode ajouter deux quaternion entre eux
     * @param quaternion
     * @return Quaternion
     */
    Quaternion ajouter(Quaternion quaternion) {
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }


    /**
     * methode de soustraction
     * @param quaternion
     * @return Quaternion
     */
    Quaternion sub(Quaternion quaternion) {
        x -= quaternion.x;
        y -= quaternion.y;
        z -= quaternion.z;
        w -= quaternion.w;
        return this;
    }


    /**
     * methode de multiplication par un autre quaternion
     * @param quaternion
     * @return Float correspondant a la somme de la multiplication des deux quaternions
     *      */
    float multiplie(Quaternion quaternion) {
        return this.x * quaternion.x + this.y * quaternion.y + this.z * quaternion.z + this.w * quaternion.w;
    }


    /**
     * methode qui multiplie tous les attributs par un scalaire
     * @param scalaire
     * @return Quaternion
     */
    Quaternion multiplieScalaire(float scalaire) {
        this.x *= scalaire;
        this.y *= scalaire;
        this.z *= scalaire;
        this.w *= scalaire;
        return this;
    }


    /**
     * methode qui si Vrai si l approximation est vrai pour toutes
     * @param quaternion
     * @param tolerance
     * @return  vrai si approximation est bonne
     *          faux sinon
     */
    boolean approximation(Quaternion quaternion, float tolerance) {
        return abs(x - quaternion.x) <= tolerance
                && abs(y - quaternion.y) <= tolerance
                && abs(z - quaternion.z) <= tolerance
                && abs(w - quaternion.w) <= tolerance;
    }


    /**
     * methoe de mutiplicatipon par un pVector
     * @param v
     * @return
     */
    PVector multipliePvector(PVector v) {
        return mult(v, new PVector());
    }


    /**
     * methode un Pvector
     * @param pVector1
     * @param pVector2
     * @return PVector
     *
     */
    PVector mult(PVector pVector1, PVector pVector2) {
        float ix = w * pVector1.x + y * pVector1.z - z * pVector1.y;
        float iy = w * pVector1.y + z * pVector1.x - x * pVector1.z;
        float iz = w * pVector1.z + x * pVector1.y - y * pVector1.x;
        float iw = -x * pVector1.x - y * pVector1.y - z * pVector1.z;
        pVector2.x = ix * w + iw * -x + iy * -z - iz * -y;
        pVector2.y = iy * w + iw * -y + iz * -x - ix * -z;
        pVector2.z = iz * w + iw * -z + ix * -y - iy * -x;
        return pVector2;
    }


    /**
     * methode qui normalise le quaternion
     * @return Quaternion
     */
    Quaternion normalise() {
        float magnetude = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w; //x^2 +Y^2 +z^2
        if (magnetude != 0.0f && magnetude != 1.0f) {
            magnetude = (float) (1.0 / Math.sqrt(magnetude));
            x *= magnetude;
            y *= magnetude;
            z *= magnetude;
            w *= magnetude;
        }
        return this;
    }


    /**
     * methode qui remet le quaternion a l echelle
     * @param scalaire
     * @return Quaternion
     */
    Quaternion remetAEchelle(float scalaire) {
        float magnetude = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w; //x^2 +Y^2 +z^2
        if (magnetude == 0.0f) {
            return this;
        } else if (magnetude == 1.0f) {
            x *= scalaire;
            y *= scalaire;
            z *= scalaire;
            w *= scalaire;
            return this;
        }
        magnetude = (float) scalaire / (float) Math.sqrt((float) magnetude);
        x *= magnetude;
        y *= magnetude;
        z *= magnetude;
        w *= magnetude;
        return this;
    }


    /**
     * methode  (Setter)  avec 4 float
     * @param x
     * @param y
     * @param z
     * @param w
     * @return
     */
    Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }


    /**
     * methode d(Setter) avec un quaternion
     * @param quaternion
     * @return Quaternion
     */
    Quaternion set(Quaternion quaternion) {
        x = quaternion.x;
        y = quaternion.y;
        z = quaternion.z;
        w = quaternion.w;
        return this;
    }


    /**
     * methode (Setter) avec un angle et un Pvector
     * @param angle
     * @param pVector
     * @return Quaternion
     */
    Quaternion set(float angle, PVector pVector) {
        float halfangle = 0.5f * angle;
        float sinhalf = (float) Math.sin((float) halfangle);
        this.x = pVector.x * sinhalf;
        this.y = pVector.y * sinhalf;
        this.z = pVector.z * sinhalf;
        this.w = (float) Math.cos(halfangle);
        return this;
    }


}

