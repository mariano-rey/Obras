package lds.obras;

/**
 * Created by jlionti on 24/7/2017.
 */

public class Obras {

    private int idObra;
    private String Nombre;
    private String Direccion;

    public Obras(int idObra, String nombre, String direccion) {
        this.idObra = idObra;
        Nombre = nombre;
        Direccion = direccion;
    }

    public int getIdObra() {
        return idObra;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getDireccion() + ")";
    }
}
