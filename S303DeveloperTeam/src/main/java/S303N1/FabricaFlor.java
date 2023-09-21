package S303N1;

public class FabricaFlor implements FabricaProducto {
    private String nombreFlor;
    private double precioFlor;
    private String colorFlor;
    public FabricaFlor(String nombreFlor, double precioFlor, String colorFlor){
        this.nombreFlor = nombreFlor;
        this.precioFlor = precioFlor;
        this.colorFlor = colorFlor;
    }
    @Override
    public Producto crearProducto() {
        return new Flor(nombreFlor, precioFlor, colorFlor);
    }
}