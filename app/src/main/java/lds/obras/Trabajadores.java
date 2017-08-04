package lds.obras;

public class Trabajadores {

    private int idTrabajador, Dni;
    private String Nombre;

    public Trabajadores(int idTrabajador, String nombre, int dni) {
        this.idTrabajador = idTrabajador;
        this.Nombre = nombre;
        this.Dni = dni;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public int getDni() {
        return Dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public String toString() {
        return "Trabajadores{" +
                "idTrabajador=" + idTrabajador +
                ", Dni=" + Dni +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }
}
