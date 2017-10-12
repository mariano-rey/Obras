package lds.obras;

public class Trabajadores {

    private int idTrabajador;
    private String Nombre;
    private int Dni;
    private boolean seleccionado;

    public Trabajadores(int idTrabajador, String Nombre, int dni) {
        this.idTrabajador = idTrabajador;
        this.Nombre = Nombre;
        this.Dni = dni;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public String getDni() {
        return "Dni: " + Dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public boolean seleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String toString() {
        return getNombre();
    }
}
