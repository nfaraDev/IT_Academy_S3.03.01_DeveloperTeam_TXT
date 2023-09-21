package S303N1;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketTXTDAO implements TicketDAO {
    private String nombreArchivo;
    public TicketTXTDAO(String nombreArchivo){
        this.nombreArchivo = nombreArchivo + ".txt";
    }

    @Override
    public List<Ticket> cargarTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals("Ticket")) {

                    int numeroTicket = Integer.parseInt(data[1]);
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date utilDate = formato.parse(data[2]);
                    Date fechaTicket = new Date(utilDate.getTime());
                    double totalVenta = Double.parseDouble(data[3]);

                    Producto producto;

                    List<LineaTicket> lineas = new ArrayList<>();
                    for (int i = 4; i < data.length; i += 7) {
                        int numLinea = Integer.parseInt(data[i]);
                        String tipo = data[i + 1];
                        String nombreProducto = data[i + 2];
                        double precioProducto = Double.parseDouble(data[i + 3]);
                        if(tipo.equals("Arbol")){
                            double altura = Double.parseDouble(data[i + 4]);
                            producto = new Arbol(nombreProducto, precioProducto, altura);
                        } else if (tipo.equals("Flor")) {
                            String color = data[i + 5];
                            producto = new Flor(nombreProducto, precioProducto, color);
                        } else {
                            String tipoMaterial = data[i + 6];
                            producto = new Decoracion(nombreProducto, precioProducto, tipoMaterial);
                        }

                        LineaTicket linea = new LineaTicket(numLinea, producto);
                        lineas.add(linea);
                    }

                    Ticket ticket = new Ticket(numeroTicket, fechaTicket, totalVenta, lineas);
                    tickets.add(ticket);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tickets;
    }


    @Override
    public void guardarTickets(List<Ticket> tickets) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {


            for (Ticket ticket : tickets) {
                int numeroTicket = ticket.getNumeroTicket();
                Date fechaTicket = ticket.getFechaTicket();
                double totalVenta = ticket.getTotalVenta();

                StringBuilder sb = new StringBuilder();

                sb.append(numeroTicket).append(",").
                    append(fechaTicket).append(",").
                    append(totalVenta).append(",");

                for (LineaTicket linea : ticket.getLineas()) {
                    Producto producto = linea.getProducto();
                    String tipo = "";

                    if (producto instanceof Arbol) {
                        tipo = "Arbol";
                        sb.append(linea.getNumLinea()).append(",")
                                .append(tipo).append(",")
                                .append(producto.getNombre()).append(",")
                                .append(producto.getPrecio()).append(",")
                                .append(((Arbol) producto).getAltura()).append(",")
                                .append("").append(",")
                                .append("").append(",")
                                .append("");
                    } else if (producto instanceof Flor) {
                        tipo = "Flor";
                        sb.append(linea.getNumLinea()).append(",")
                                .append(tipo).append(",")
                                .append(producto.getNombre()).append(",")
                                .append(producto.getPrecio()).append(",")
                                .append("0.0").append(",")
                                .append(((Flor) producto).getColor()).append(",")
                                .append("").append(",")
                                .append("");
                    } else if (producto instanceof Decoracion) {
                        tipo = "Decoracion";
                        sb.append(linea.getNumLinea()).append(",")
                                .append(tipo).append(",")
                                .append(producto.getNombre()).append(",")
                                .append(producto.getPrecio()).append(",")
                                .append("0.0").append(",")
                                .append("").append(",")
                                .append(((Decoracion) producto).getTipoMaterial()).append(",")
                                .append("");
                    }
                }
                writer.write("Ticket," + sb.toString());
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}




