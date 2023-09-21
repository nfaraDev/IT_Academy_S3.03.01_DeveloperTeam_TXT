package S303N1;

import java.util.List;

public interface TicketDAO {

    List<Ticket> cargarTickets();
    void guardarTickets(List<Ticket> tickets);
}
