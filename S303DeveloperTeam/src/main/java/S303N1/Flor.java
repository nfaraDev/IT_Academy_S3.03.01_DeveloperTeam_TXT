package S303N1;

public class Flor extends Producto {
    private String color;

    public Flor(String nombre, double precio, String color) {
        super(nombre, precio);
        this.color = color;
    }

    // Getter
    public String getColor() {
        return color;
    }

}
