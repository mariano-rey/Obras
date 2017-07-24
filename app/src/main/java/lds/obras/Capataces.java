package lds.obras;

/**
 * Created by jlionti on 24/7/2017.
 */

public class Capataces {

    private int idCapataz;
    private String Nombre;

    public Capataces(int idCapataz, String Nombre) {
        this.idCapataz = idCapataz;
        this.Nombre = Nombre;
    }

    public int getIdCapataz() {
        return idCapataz;
    }

    public String getNombre() {
        return Nombre;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
