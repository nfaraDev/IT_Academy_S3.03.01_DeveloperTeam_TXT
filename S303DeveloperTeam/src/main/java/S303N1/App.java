package S303N1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    //   COMPROBAMOS SI HAY DATOS PREVIOS DE LA MISMA FLORISTERIA (METODO STATIC)
    private static String obtenerNombreArchivoPrevio(String directorio, String nombreFloristeria) {
        File folder = new File(directorio);
        File[] archivos = folder.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    String nombreArchivo = archivo.getName().replace(".txt", "");
                    if (nombreArchivo.equalsIgnoreCase(nombreFloristeria)) {
                        return archivo.getName();    //devuelve el nombre del txt sólo si hay datos previos
                    }
                }
            }
        }
        return null;
    }

    //    ****************************** MAIN ******************************

        public static void main(String[] args) {

            //   [ABRIMOS FLORISTERIA Y CARGAMOS SUS DATOS] ó [CREAMOS FLORISTERIA NUEVA]

            Scanner input = new Scanner(System.in);
            System.out.println("Nombre de la floristería: ");
            String nombreFloristeria = input.nextLine();
            Floristeria floristeria = new Floristeria(nombreFloristeria);    //*****INSTANCIAMOS*****

            // Directorio donde se encuentran los archivos de persistencia
            String directorioPersistencia = ".";

            // Obtener el nombre del archivo previo
            String nombreArchivoPrevio = obtenerNombreArchivoPrevio(directorioPersistencia, nombreFloristeria);

            if (nombreArchivoPrevio != null) {

                //****CASO 1 DE 2:**** LA FLORISTERIA YA TIENE DATOS: LOS CARGAMOS EN EL CATALOGO

                floristeria.setNombre(nombreArchivoPrevio.replace(".txt", ""));
                System.out.println("Existen datos anteriores de " + nombreArchivoPrevio.replace(".txt", ""));
                System.out.println("         cargando datos previos...");
                ProductoDAO productoDao = new ProductoTXTDAO(floristeria.getNombre());
                TicketDAO ticketDao = new TicketTXTDAO(floristeria.getNombre());
                List<Producto> productos = productoDao.cargarProductos();
                List<Ticket> tickets = ticketDao.cargarTickets();
                floristeria.setCatalogo(productos);
                floristeria.setHistoricoTickets((ArrayList<Ticket>) tickets);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else {

                //****CASO 2 DE 2:**** FLORISTERIA NUEVA: MENÚ "INICIAL" DE ENTRADA DE LOS PRIMEROS DATOS

                boolean out = false;
                do{
                    //MENÚ PREVIO DE ARRANQUE DE FLORISTERIA:
                    System.out.println( "\nMenú de arranque de nueva floristería:\n"+
                                        "\n 1 - Añadir primer arbol"+
                                        "\n 2 - Añadir primera flor"+
                                        "\n 3 - Añadir primera decoración"+
                                        "\n 0 - Seguir a menú principal (sólo tras haber entrado datos)");
                    int opcionMenu0 = input.nextInt();

                    switch (opcionMenu0) {
                        case 0:
                            if(floristeria.getCatalogo().isEmpty()){
                                System.out.println("Antes de seguir debes haber introducido algún dato");
                            } else {
                                out = true;
                            }
                            break;
                        case 1:
                            agregarArbol(floristeria);
                            break;
                        case 2:
                            agregarFlor(floristeria);
                            break;
                        case 3:
                            agregarDecoracion(floristeria);
                            break;
                        default:
                            System.out.println("Inténtalo de nuevo");
                    }

                } while(!out);
            }

            //MENÚ PRINCIPAL. AL SALIR, SE GUARDAN LOS DATOS EN [NOMBRE_FLORISTERIA].TXT

            boolean salir = false;
            int opcionMenu;
            do {
                System.out.println( "\nMENU PRINCIPAL:"+
                                    "\n 1 - Añadir arbol"+
                                    "\n 2 - Añadir flor"+
                                    "\n 3 - Añadir decoración"+
                                    "\n 4 - Listado de stock"+
                                    "\n 5 - Eliminar arbol"+
                                    "\n 6 - Eliminar flor"+
                                    "\n 7 - Eliminar decoración"+
                                    "\n 8 - Valor de existencias"+
                                    "\n 9 - Registrar una venta e imprimir ticket"+
                                    "\n10 - Listado histórico de tickets"+
                                    "\n11 - Acumulado de ventas"+
                                    "\n 0 - Guardar datos y SALIR");

                opcionMenu = input.nextInt();
                input.nextLine();

                switch (opcionMenu) {
                    case 0:
                        ProductoDAO daoP = new ProductoTXTDAO(nombreFloristeria);
                        TicketDAO daoT = new TicketTXTDAO(nombreFloristeria);
                        daoP.guardarProductos(floristeria.getCatalogo());
                        daoT.guardarTickets(floristeria.getHistoricoTickets());
                        salir = true;
                        break;
                    case 1:
                        agregarArbol(floristeria);
                        break;
                    case 2:
                        agregarFlor(floristeria);
                        break;
                    case 3:
                        agregarDecoracion(floristeria);
                        break;
                    case 4:
                        floristeria.mostrarCatalogo();
                        break;
                    case 5:
                        System.out.println("Nombre del árbol a eliminar: ");
                        String nombreArbolAEliminar = input.nextLine();
                        floristeria.retirarProducto(nombreArbolAEliminar);
                        break;
                    case 6:
                        System.out.println("Nombre de la flor a eliminar: ");
                        String nombreFlorAEliminar = input.nextLine();
                        floristeria.retirarProducto(nombreFlorAEliminar);
                        break;
                    case 7:
                        System.out.println("Nombre de la decoración a eliminar: ");
                        String nombreDecorAEliminar = input.nextLine();
                        floristeria.retirarProducto(nombreDecorAEliminar);
                        break;
                    case 8:
                        System.out.println("\nValor de existencias de '" + floristeria.getNombre() + "' = €" + floristeria.calcularValorTotal());
                        System.out.println("***********************************");
                        floristeria.mostrarStock();
                        break;
                    case 9:
                        Ticket ticket = null;
                        try {
                            ticket = floristeria.generarTicket();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        ticket.verTicket();
                        break;
                    case 10:
                        floristeria.imprimirTickets();
                        break;
                    case 11:
                        floristeria.mostrarVentasTotal();
                        break;
                    default:
                        System.out.println("Inténtalo de nuevo");
                }
            } while (!salir);
            input.close();

        }

    private static void agregarArbol(Floristeria floristeria) {
        Scanner input = new Scanner(System.in);

        System.out.println("Nombre del arbol: ");
        String nombreArbol = input.nextLine();

        System.out.println("Precio (EUR) del arbol '"+ nombreArbol +"'");
        String inputPrecioArbol = input.nextLine();
        // Reemplazar puntos por comas en el precio ingresado
        inputPrecioArbol = inputPrecioArbol.replace(',', '.');
        double precioArbol = 0;
        try {
            precioArbol = Double.parseDouble(inputPrecioArbol);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número válido.");
            System.exit(1);
        }

        System.out.println("Altura (mts) del arbol '"+ nombreArbol +"'");
        String inputAlturaArbol = input.nextLine();
        // Reemplazar puntos por comas en la altura ingresada
        inputAlturaArbol = inputAlturaArbol.replace(',', '.');
        double alturaArbol = 0;
        try {
            alturaArbol = Double.parseDouble(inputAlturaArbol);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número válido.");
            System.exit(1);
        }

        FabricaProducto fabricaArbol = new FabricaArbol(nombreArbol, precioArbol, alturaArbol);
        Producto arbol = fabricaArbol.crearProducto();
        floristeria.agregarProducto(arbol);
    }

    private static void agregarFlor(Floristeria floristeria){
        Scanner input = new Scanner(System.in);

        System.out.println("Nombre de la flor: ");
        String nombreFlor = input.nextLine();

        System.out.println("Precio (EUR) de la flor '"+ nombreFlor +"'");
        String inputPrecioFlor = input.nextLine();
        // Reemplazar puntos por comas en el precio ingresado
        inputPrecioFlor = inputPrecioFlor.replace(',', '.');
        double precioFlor = 0;
        try {
            precioFlor = Double.parseDouble(inputPrecioFlor);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número válido.");
            System.exit(1);
        }

        System.out.println("Color de la flor '"+ nombreFlor +"'");
        String colorFlor = input.nextLine();
        FabricaProducto fabricaFlor = new FabricaFlor(nombreFlor, precioFlor, colorFlor);
        Producto flor = fabricaFlor.crearProducto();
        floristeria.agregarProducto(flor);
    }

    private static void agregarDecoracion(Floristeria floristeria){
        Scanner input = new Scanner(System.in);

        System.out.println("Nombre de la decoración: ");
        String nombreDeco = input.nextLine();

        System.out.println("Precio (EUR) de la decoración '"+ nombreDeco +"'");
        String inputPrecioDeco = input.nextLine();
        // Reemplazar puntos por comas en el precio ingresado
        inputPrecioDeco = inputPrecioDeco.replace(',', '.');
        double precioDeco = 0;
        try {
            precioDeco = Double.parseDouble(inputPrecioDeco);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un número válido.");
            System.exit(1);
        }

        System.out.println("Tipo de material de '"+ nombreDeco +"'");
        String tipoMaterial = input.nextLine();

        FabricaProducto fabricaDecoracion = new FabricaDecoracion(nombreDeco, precioDeco, tipoMaterial);
        Producto decoracion = fabricaDecoracion.crearProducto();
        floristeria.agregarProducto(decoracion);
    }

}




