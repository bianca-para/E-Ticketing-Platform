package org.dev.server;

import org.dev.server.services.EventService;
import org.dev.server.services.VenueService;
import org.dev.server.services.UserService;
import org.dev.server.services.TicketService;

import org.dev.server.models.Venue;
import org.dev.server.models.Event;
import org.dev.server.models.Category;
import org.dev.server.models.User;
import org.dev.server.models.Participant;
import org.dev.server.models.Ticket;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting EventPlannerBP...");

        try {
            //initializare servicii
            EventService eventService = EventService.getInstance();
            VenueService venueService = VenueService.getInstance();
            UserService userService = UserService.getInstance();
            TicketService ticketService = TicketService.getInstance();


            //testare operatii CRUD

            System.out.println("\nCreating venue");
            Venue venue = new Venue("v1", "Sala Mare", "Bd. Benjamin Franklin 1-3", 500);
            venueService.createVenue(venue);

            System.out.println("\nCreating user");
            User user = new Participant("u1", "Eliza Popa", "popa_eliza26@gmail.com");
            userService.registerUser(user);

            System.out.println("\nCreating eveniment");
            Event event = new Event("e1", "Concert Maroon5", Category.MUSIC, venue,
                    LocalDateTime.of(2025, 6, 21, 19, 30), 125.0);
            eventService.createEvent(event);

            System.out.println("\nListing all events:");
            eventService.listEvents().forEach(System.out::println);

            System.out.println("\nEvents by category (MUSIC):");
            eventService.searchEventsByCategory(Category.MUSIC).forEach(System.out::println);

            System.out.println("\nListing all venues:");
            venueService.listVenues().forEach(System.out::println);

            System.out.println("\nListing all users:");
            userService.listUsers().forEach(System.out::println);

            System.out.println("\nUpdating event price");
            event.setPrice(80.0);
            eventService.updateEvent(event);

            System.out.println("\nBuying ticket");
            Ticket ticket = ticketService.buyTicket("e1", "u1");
            System.out.println("Ticket purchased: " + ticket);

            System.out.println("\nRegistering attendee");
            eventService.registerAttendee("e1", "u1");

            System.out.println("\nEvent attendees:");
            eventService.listAttendees("e1").forEach(System.out::println);

            System.out.println("\nUser tickets:");
            ticketService.listTicketsForUser("u1").forEach(System.out::println);


        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}