package S303N1;

public class FabricaDecoracion implements FabricaProducto {
    private String nombreDeco;
    private double precioDeco;
    private String tipoMaterial;
    public FabricaDecoracion(String nombreDeco, double precioDeco, String tipoMaterial){
        this.nombreDeco = nombreDeco;
        this.precioDeco = precioDeco;
        this.tipoMaterial = tipoMaterial;
    }
    @Override
    public Producto crearProducto() {
        return new Decoracion(nombreDeco, precioDeco, tipoMaterial);
    }
}
