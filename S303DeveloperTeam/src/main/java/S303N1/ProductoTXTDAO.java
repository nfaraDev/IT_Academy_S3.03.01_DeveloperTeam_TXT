package S303N1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ProductoTXTDAO implements ProductoDAO {
    private String nombreArchivo;
    public ProductoTXTDAO(String nombreArchivo){
        this.nombreArchivo = nombreArchivo + ".txt";
    }

    @Override
    public List<Producto> cargarProductos() {

        List<Producto> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Línea leída: " + line);
                String[] data = line.split(",");
                if(!data[0].equals("Ticket")) {
                    String tipo = data[0];
                    String nombre = data[1];
                    double precio = Double.parseDouble(data[2]);

                    if (tipo.equals("Arbol")) {
                        double altura = Double.parseDouble(data[3]);
                        productos.add(new Arbol(nombre, precio, altura));
                    } else if (tipo.equals("Flor")) {
                        String color = data[3];
                        productos.add(new Flor(nombre, precio, color));
                    } else if (tipo.equals("Decoracion")) {
                        String tipoMaterial = data[3];
                        productos.add(new Decoracion(nombre, precio, tipoMaterial));
                    }
                } else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;
    }


    @Override
    public void guardarProductos(List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, false))) {

            for (Producto producto : productos) {
                String tipo = "";

                if (producto instanceof Arbol) {
                    tipo = "Arbol";
                    double altura = ((Arbol) producto).getAltura();
                    writer.write(tipo + "," + producto.getNombre() + "," + producto.getPrecio() + "," + altura);
                } else if (producto instanceof Flor) {
                    tipo = "Flor";
                    String color = ((Flor) producto).getColor();
                    writer.write(tipo + "," + producto.getNombre() + "," + producto.getPrecio() + "," + color);
                } else if (producto instanceof Decoracion) {
                    tipo = "Decoracion";
                    String tipoMaterial = ((Decoracion) producto).getTipoMaterial();
                    writer.write(tipo + "," + producto.getNombre() + "," + producto.getPrecio() + "," + tipoMaterial);
                }

                writer.newLine();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}




