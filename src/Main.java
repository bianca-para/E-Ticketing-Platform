import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService userSvc = new UserService();
        EventService evSvc = new EventService();
        TicketService ticketSvc = new TicketService();

        Participant p1 = new Participant("u1", "Mara",   "mimargi2005@gmail.com");
        Admin a1 = new Admin      ("u2", "Tudor","tudorpredescu00@yahoo.com");
        userSvc.registerUser(p1);
        userSvc.registerUser(a1);

        System.out.println("Find user u1: " + userSvc.findById("u1"));

        Venue v1 = new Venue(
                "v1",
                "Sala Mare",
                "Bd. Benjamin Franklin 1-3",
                500
        );
        Event e1 = new Event(
                "e1",
                "Concert Jazz",
                Category.MUSIC,
                v1,
                LocalDateTime.of(2025, 5, 21, 19, 30),
                75.0
        );
        evSvc.createEvent(e1);

        System.out.println("\nToate evenimentele:");
        evSvc.listEvents().forEach(System.out::println);

        System.out.println("\nEvenimente de muzica:");
        evSvc.searchByCategory(Category.MUSIC).forEach(System.out::println);

        e1.setPrice(80.0);
        evSvc.updateEvent(e1);
        System.out.println("\nEvenimentul e1 dupa update (price = 80): " + e1);

        evSvc.registerAttendee("e1", "u1");

        System.out.println("\nParticipanti la e1: " + evSvc.listAttendees("e1"));

        Ticket t1 = ticketSvc.buyTicket("e1", "u1");
        System.out.println("\nBilet cumparat: " + t1);
        System.out.println("Bilete pentru Mara: " + ticketSvc.listTicketsForUser("u1"));

        ticketSvc.cancelTicket(t1.getId());
        System.out.println("Bilete pentru Mara dupa anulare: " + ticketSvc.listTicketsForUser("u1"));
    }
}
