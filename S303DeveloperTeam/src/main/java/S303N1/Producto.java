package S303N1;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters (setters no hacen falta)
    public String getNombre() {
        return nombre;
    }


    public double getPrecio() {
        return precio;
    }


}
