package S303N1;

import java.util.List;

public interface ProductoDAO {
    List<Producto> cargarProductos();

    void guardarProductos(List<Producto> productos);
}
