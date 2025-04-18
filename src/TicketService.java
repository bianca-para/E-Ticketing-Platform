import java.time.LocalDateTime;
import java.util.*;

public class TicketService {
    private List<Ticket> tickets = new ArrayList<>();

    public Ticket buyTicket(String eventId, String userId) {
        String ticketId = UUID.randomUUID().toString();
        Ticket t = new Ticket(ticketId, eventId, userId, LocalDateTime.now());
        tickets.add(t);
        return t;
    }

    public boolean cancelTicket(String ticketId) {
        return tickets.removeIf(t -> t.getId().equals(ticketId));
    }

    public List<Ticket> listTicketsForUser(String userId) {
        List<Ticket> rez = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getUserId().equals(userId)) rez.add(t);
        }
        return rez;
    }
}
