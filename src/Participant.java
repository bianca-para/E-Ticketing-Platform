import java.util.ArrayList;
import java.util.List;

public class Participant extends User {
    private List<String> purchasedTicketIds = new ArrayList<>();

    public Participant(String id, String name, String email) {
        super(id, name, email);
    }

    public List<String> getPurchasedTicketIds() {
        return purchasedTicketIds;
    }

    public void setPurchasedTicketIds(List<String> purchasedTicketIds) {
        this.purchasedTicketIds = purchasedTicketIds;
    }

    public void addTicket(String ticketId) {
        this.purchasedTicketIds.add(ticketId);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tickets=" + purchasedTicketIds +
                '}';
    }
}
