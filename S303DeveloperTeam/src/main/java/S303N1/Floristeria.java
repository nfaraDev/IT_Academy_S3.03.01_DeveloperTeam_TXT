package S303N1;

import java.util.*;

public class Floristeria {


    //Atributos
    private ArrayList<Ticket> tickets;
    private String nombre;
    private static String nombreArchivo;    //fichero donde se guardarán los datos, con el nombre de la floristería
    private List<Producto> catalogo;
    private double valorTotalInventario;

    //Constructor
    public Floristeria(String nombre) {
        this.nombre = nombre;
        this.catalogo = new ArrayList<>();
        this.tickets = new ArrayList<>();

    }
    // Getters
    public String getNombre(){
        return this.nombre;
    }
    public String getNombreArchivo () { return nombreArchivo;   }
    public List<Producto> getCatalogo () {
        return catalogo;
    }
    public List<Ticket> getHistoricoTickets () {
        return tickets;
    }


    //Setters

    public void  setNombre(String nuevoNombre) {
        this.nombre = nuevoNombre;
    }
    public void setCatalogo (List<Producto> catalogoAnterior) {
        this.catalogo = catalogoAnterior;
    }
    public void setHistoricoTickets (ArrayList<Ticket> historicoTickets) {
        this.tickets = historicoTickets;
    }


    //Metodos generales
    //recibe un objeto Producto y lo agrega al catálogo de la floristería.
    public void agregarProducto(Producto producto) {
        catalogo.add(producto);
    }

    //recibe un objeto Producto y lo retira del catálogo de la floristería.
    public void retirarProducto(String nombreProducto) {
        try {
            // Buscar el producto en el catálogo
            Iterator<Producto> iter = catalogo.iterator();
            boolean encontrado = false;

            while (iter.hasNext()) {
                Producto producto = iter.next();
                if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                    catalogo.remove(producto);
                    System.out.println("Producto eliminado: " + nombreProducto);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("El producto no está en el catálogo.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al retirar el producto: " + e.getMessage());
        }
    }

    //muestra por consola el catálogo de la floristería, mostrando todos los atributos de cada producto
    public void mostrarCatalogo() {
        System.out.println("\nCatálogo de la floristeria '" + Floristeria.this.getNombre() + "':\n");

        int stockArboles = 0;
        boolean hayArboles = false;//variable boolean para verificar si hay arboles
        System.out.println(" ***ÁRBOLES :");
        for (Producto producto : catalogo) { //dentro del bucle si hay arboles imprime detalles e incrementa  la variable
            if (producto instanceof Arbol) {
                System.out.print("    " + producto.getNombre() + " - Precio: €" + producto.getPrecio());
                System.out.println(" - Altura: " + ((Arbol) producto).getAltura() + "m");
                stockArboles++;
                hayArboles = true;//establece en verdadero si hay al menos un arbol
            }
        }
        if (!hayArboles) { //si es diferente a hayarboles imprime no stock
            System.out.println("    Sin stock de Árboles");
        }

        int stockFlores = 0;
        boolean hayFlores = false;
        System.out.println(" ***FLORES :");
        for (Producto producto : catalogo) {
            if (producto instanceof Flor) {
                System.out.print("    " + producto.getNombre() + " - Precio: €" + producto.getPrecio());
                System.out.println(" - Color: " + ((Flor) producto).getColor());
                stockFlores++;
                hayFlores = true;
            }
        }
        if (!hayFlores) {
           System.out.println("    Sin stock de Flores");
        }

        int stockDecoraciones = 0;
        boolean hayDecoraciones = false;
        System.out.println(" ***DECORACIONES :");
        for (Producto producto : catalogo) {
            if (producto instanceof Decoracion) {
                System.out.print("    " + producto.getNombre() + " - Precio: €" + producto.getPrecio());
                System.out.println(" - Material: " + ((Decoracion) producto).getTipoMaterial());
                stockDecoraciones++;
                hayDecoraciones = true;
            }
        }
        if (!hayDecoraciones) {
            System.out.println("    Sin stock de Decoraciones");

        }

    }

    //Generar TICKET
    public Ticket generarTicket() throws InterruptedException {
        Ticket nuevoTicket = new Ticket();
        generarVenta(nuevoTicket);
        tickets.add(nuevoTicket);    // Agregar el ticket a la lista de tickets
        return nuevoTicket;
    }

    private void generarVenta(Ticket nuevoTicket) {
        Scanner input = new Scanner(System.in);
        boolean agregarProductos = true;

        while (agregarProductos) {
            // Muestra el catálogo al usuario
            this.mostrarCatalogo();

            // Pide al usuario que seleccione un producto
            System.out.println("\nPor favor, escribe el nombre del producto que deseas comprar:");
            String nombreProducto = input.nextLine();

            // Comprueba si el producto está en el catálogo
            Producto productoSeleccionado = null;
            for (Producto producto : this.catalogo) {
                if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                    productoSeleccionado = producto;
                    break;
                }
            }

            // Si el producto no está en el catálogo, muestra un mensaje de error y vuelve a pedir al usuario que elija
            if (productoSeleccionado == null) {
                System.out.println("Lo siento, no tenemos ese producto. Intenta de nuevo.");
                continue;
            }

            // Añade la línea al ticket
            nuevoTicket.agregarLinea(nuevoTicket.getNumeroTicket(), productoSeleccionado);
            catalogo.remove(productoSeleccionado);    //quitamos del stock

            // Pregunta al usuario si desea agregar más productos al ticket
            System.out.println("¿Deseas agregar más productos al ticket? (si/no)");
            String respuesta = input.nextLine();
            if (respuesta.equalsIgnoreCase("no")) {
                agregarProductos = false;
            }

        }
    }


    //muestra por consola el stock de la floristería, es decir, los productos del catálogo
    public void mostrarStock () {
        System.out.println("Stock de la floristería:\n");
        int cantidad = 0;
        int cantidadArboles = 0;
        int cantidadFlores = 0;
        int cantidadDecoraciones = 0;
        double valorInventario = 0.0;
        double valorArboles = 0.0;
        double valorFlores = 0.0;
        double valorDecoraciones = 0.0;
        for (Producto producto : catalogo) {
            if (producto instanceof Arbol) {
                cantidadArboles++;
                valorArboles += producto.getPrecio();
                System.out.println("1 un.ARBOL: " + producto.getNombre() + " valorado en: " + producto.getPrecio() + "EUR");
            } else if (producto instanceof Flor) {
                cantidadFlores++;
                valorFlores += producto.getPrecio();
                System.out.println("1 un.FLOR: " + producto.getNombre() + " valorada en: " + producto.getPrecio() + "EUR");
            } else if (producto instanceof Decoracion) {
                cantidadDecoraciones++;
                valorDecoraciones += producto.getPrecio();
                System.out.println("1 un.DECORACION: " + producto.getNombre() + " valorada en: " + producto.getPrecio() + "EUR");
            }
        }
        cantidad = cantidadArboles + cantidadFlores + cantidadDecoraciones;
        valorInventario = valorArboles + valorFlores + valorDecoraciones;
        System.out.println("\nPRODUCTOS   " + "CANTIDAD  "  + "IMPORTE(EUR)");
        System.out.println("--------------------------------");
        System.out.println("ARBOLES       " + cantidadArboles + "     " + valorArboles);
        System.out.println("FLORES        " + cantidadFlores + "     " + valorFlores);
        System.out.println("DECORACIONES  " + cantidadDecoraciones + "     " + valorDecoraciones);
        System.out.println("--------------------------------");
        System.out.println("TOTAL         " + cantidad + "     " + valorInventario);
    }


    //
    public double calcularValorTotal () {
        double valorTotalInventario = 0.0;
        for (Producto producto : catalogo){
            valorTotalInventario += producto.getPrecio();
        }
        return valorTotalInventario;
    }


    //muestra por consola el total de todas las ventas registradas en los tickets.
    public void mostrarVentasTotal() {
        double valorTotal = 0.0;
        for (Ticket ticket : tickets) {
            valorTotal = valorTotal + ticket.getTotalVenta();
        }
        System.out.println("Ventas totales de la floristería: EUR " + valorTotal);
    }

    //imprime el histórico de Tickets de la Floristería
    public void imprimirTickets(){
        for(Ticket ticket : tickets){
            System.out.println("Ticket nº " + ticket.getNumeroTicket());
            System.out.println(" Fecha de venta: " + ticket.getFechaTicket());
            System.out.println(" Artículos:");
            for (LineaTicket linea : ticket.getLineas()) {
                System.out.println("   " + linea.getNumLinea() + " - 1ud." + linea.getProducto().getNombre() + " - €" + linea.getPrecio());
            }
            System.out.println(" Importe Total: €" + ticket.getTotalVenta());
            System.out.println("----\n");
        }
    }

}






