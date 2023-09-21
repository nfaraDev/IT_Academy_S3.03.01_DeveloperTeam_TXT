package S303N1;
import java.io.Serializable;
import java.util.ArrayList;

public class LineaTicket implements Serializable {
    private int numLinea = 0;
    private Producto producto;
    private String nombreProducto;
    private double precioProducto;
    private String tipo;
    private double altura;
    private String color;
    private String material;


    public LineaTicket(int numLinea, Producto producto) {
        this.numLinea = numLinea;
        this.producto = producto;
    }

    //Getters
    public int getNumLinea() { return numLinea; }
    public Producto getProducto() { return producto; }
    public double getPrecio() { return producto.getPrecio(); }

    //Setters
    public void setNumLinea(int numLinea){
        this.numLinea = numLinea;
    }


}
