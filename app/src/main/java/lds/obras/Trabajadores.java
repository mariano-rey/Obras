package lds.obras;

public class Trabajadores {

    private int idTrabajador, dni;
    private String Nombre;

    public Trabajadores(int idTrabajador, String nombre, int dni) {
        this.idTrabajador = idTrabajador;
        this.Nombre = nombre;
        this.dni = dni;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return Nombre;
    }
}
