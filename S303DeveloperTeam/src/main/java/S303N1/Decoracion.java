package S303N1;

public class Decoracion extends Producto {
    private String tipoMaterial;

    public Decoracion(String nombre, double precio, String tipoMaterial) {
        super(nombre, precio);
        this.tipoMaterial = tipoMaterial;
    }

    // Getter
    public String getTipoMaterial() {
        return tipoMaterial;
    }

}
