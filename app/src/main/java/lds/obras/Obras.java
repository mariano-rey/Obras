package lds.obras;

public class Obras {

    private int idObra;
    private String Nombre;
    private String Direccion;

    public Obras(int idObra, String nombre, String direccion) {
        this.idObra = idObra;
        this.Nombre = nombre;
        this.Direccion = direccion;
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

    public String toString() {
        return getNombre() + " (" + getDireccion() + ")";
    }
}
