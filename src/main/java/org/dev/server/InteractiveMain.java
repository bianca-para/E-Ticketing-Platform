package org.dev.server;

import org.dev.server.config.DatabaseConfig;
import org.dev.server.models.*;
import org.dev.server.services.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class InteractiveMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static VenueService venueService;
    private static UserService userService;
    private static EventService eventService;
    private static TicketService ticketService;
    private static AuditService auditService;

    public static void main(String[] args) {
        try {
            System.out.println("Starting EventPlannerBP Interactive Mode...");
            DatabaseConfig.getInstance();

            venueService = VenueService.getInstance();
            userService = UserService.getInstance();
            eventService = EventService.getInstance();
            ticketService = TicketService.getInstance();
            auditService = AuditService.getInstance();

            System.out.println("All services initialized successfully!");

            initializeTestData();

            showMainMenu();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void initializeTestData() {

        try {
            createSampleVenues();
            createSampleUsers();
            createSampleEvents();

            System.out.println("Test data initialized!");
        } catch (Exception e) {
            System.out.println("Some test data might already exist (this is normal)");
        }
    }

    private static void createSampleVenues() {
        try {
            venueService.createVenue(new Venue("v2", "Sala Palatului", "Str. Ion Campineanu 28", 4000));
            venueService.createVenue(new Venue("v3", "Arena Nationala", "Bd. Basarabia 37-39", 55000));
            venueService.createVenue(new Venue("v4", "Teatrul National Bucuresti", "Bd. Nicolae Balcescu 2", 800));
            venueService.createVenue(new Venue("v5", "Ateneul Roman", "Str. Lipscani 5", 200));
            venueService.createVenue(new Venue("v6", "Romexpo", "Bd. Marastiti 65-67", 10000));
        } catch (Exception e) {
        }
    }

    private static void createSampleUsers() {
        try {
            userService.registerUser(new Participant("u2", "Mara Marginean", "mimargi2005@gmail.com"));
            userService.registerUser(new Participant("u3", "Alex Cristea", "alexcristea@yahoo.com"));
            userService.registerUser(new Moderator("m1", "Tudor Predescu", "predi2001@admin.com"));
            userService.registerUser(new Participant("u4", "Sofia Voicu", "sofi_voiq@gmail.com"));
        } catch (Exception e) {
        }
    }

    private static void createSampleEvents() {
        try {
            Venue venue1 = venueService.findVenueById("v1");
            Venue venue2 = venueService.findVenueById("v2");
            Venue venue3 = venueService.findVenueById("v3");

            if (venue1 != null) {
                eventService.createEvent(new Event("e2", "Festivalul de muzica clasica George Enescu", Category.MUSIC, venue1,
                        LocalDateTime.of(2025, 7, 15, 20, 0), 150.0));
            }
            if (venue2 != null) {
                eventService.createEvent(new Event("e3", "Finala turneului national de tenis", Category.SPORT, venue2,
                        LocalDateTime.of(2025, 8, 20, 21, 30), 80.0));
            }
            if (venue3 != null) {
                eventService.createEvent(new Event("e4", "Romeo si Julieta", Category.THEATER, venue3,
                        LocalDateTime.of(2025, 6, 10, 19, 0), 55.0));
            }
        } catch (Exception e) {
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("EVENT PLANNER BP - MAIN MENU");
            System.out.println("1. Venue Management");
            System.out.println("2. User Management");
            System.out.println("3. Event Management");
            System.out.println("4. Ticket Management");
            System.out.println("0. Exit");
            System.out.println("=".repeat(50));
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> showVenueMenu();
                    case 2 -> showUserMenu();
                    case 3 -> showEventMenu();
                    case 4 -> showTicketMenu();
                    case 0 -> {
                        System.out.println("Goodbye! Thanks for using EventPlannerBP!");
                        return;
                    }
                    default -> System.out.println("Invalid option! Please choose 0-7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static void showVenueMenu() {
        while (true) {
            System.out.println("\nVENUE MANAGEMENT");
            System.out.println("1. Create New Venue");
            System.out.println("2. List All Venues");
            System.out.println("3. Find Venue by ID");
            System.out.println("4. Find Venue by Name");
            System.out.println("5. Update Venue");
            System.out.println("6. Delete Venue");
            System.out.println("7. Search by Capacity Range");
            System.out.println("8. Get Large Venues");
            System.out.println("9. Search by Location");
            System.out.println("10. Check Venue Availability");
            System.out.println("11. Average Venue Capacity");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> createVenue();
                    case 2 -> listAllVenues();
                    case 3 -> findVenueById();
                    case 4 -> findVenueByName();
                    case 5 -> updateVenue();
                    case 6 -> deleteVenue();
                    case 7 -> searchVenuesByCapacity();
                    case 8 -> getLargeVenues();
                    case 9 -> searchVenuesByLocation();
                    case 10 -> checkVenueAvailability();
                    case 11 -> getAverageCapacity();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static void createVenue() {
        System.out.println("\nCREATE NEW VENUE");
        System.out.print("Enter venue ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter venue name: ");
        String name = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter capacity: ");
        try {
            int capacity = Integer.parseInt(scanner.nextLine());

            Venue venue = new Venue(id, name, address, capacity);
            venueService.createVenue(venue);
            System.out.println("Venue created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity number!");
        } catch (Exception e) {
            System.out.println("Error creating venue: " + e.getMessage());
        }
    }

    private static void listAllVenues() {
        System.out.println("\nALL VENUES:");
        try {
            List<Venue> venues = venueService.listVenues();
            if (venues.isEmpty()) {
                System.out.println("No venues found.");
            } else {
                venues.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error listing venues: " + e.getMessage());
        }
    }

    private static void findVenueById() {
        System.out.print("Enter venue ID: ");
        String id = scanner.nextLine();

        try {
            Venue venue = venueService.findVenueById(id);
            if (venue != null) {
                System.out.println("Found: " + venue);
            } else {
                System.out.println("Venue not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findVenueByName() {
        System.out.print("Enter venue name: ");
        String name = scanner.nextLine();

        try {
            Venue venue = venueService.findVenueByName(name);
            if (venue != null) {
                System.out.println("Found: " + venue);
            } else {
                System.out.println("Venue not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateVenue() {
        System.out.print("Enter venue ID to update: ");
        String id = scanner.nextLine();

        try {
            Venue existingVenue = venueService.findVenueById(id);
            if (existingVenue == null) {
                System.out.println("Venue not found!");
                return;
            }

            System.out.println("Current venue: " + existingVenue);
            System.out.print("Enter new name (press Enter to keep current): ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = existingVenue.getName();

            System.out.print("Enter new address (press Enter to keep current): ");
            String address = scanner.nextLine();
            if (address.trim().isEmpty()) address = existingVenue.getAddress();

            System.out.print("Enter new capacity (press Enter to keep current): ");
            String capacityStr = scanner.nextLine();
            int capacity = capacityStr.trim().isEmpty() ? existingVenue.getCapacity() : Integer.parseInt(capacityStr);

            Venue updatedVenue = new Venue(id, name, address, capacity);
            venueService.updateVenue(updatedVenue);
            System.out.println("Venue updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating venue: " + e.getMessage());
        }
    }

    private static void deleteVenue() {
        System.out.print("Enter venue ID to delete: ");
        String id = scanner.nextLine();

        try {
            Venue venue = venueService.findVenueById(id);
            if (venue == null) {
                System.out.println("Venue not found!");
                return;
            }

            System.out.println("Are you sure you want to delete: " + venue.getName() + "? (yes/no)");
            String confirm = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirm)) {
                venueService.deleteVenue(id);
                System.out.println("Venue deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting venue: " + e.getMessage());
        }
    }

    private static void searchVenuesByCapacity() {
        try {
            System.out.print("Enter minimum capacity: ");
            int min = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter maximum capacity: ");
            int max = Integer.parseInt(scanner.nextLine());

            List<Venue> venues = venueService.getVenuesByCapacity(min, max);
            System.out.println("\nVenues with capacity " + min + "-" + max + ":");
            venues.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getLargeVenues() {
        try {
            System.out.print("Enter minimum capacity: ");
            int minCapacity = Integer.parseInt(scanner.nextLine());

            List<Venue> venues = venueService.getLargeVenues(minCapacity);
            System.out.println("\nLarge venues (min " + minCapacity + " capacity):");
            venues.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchVenuesByLocation() {
        System.out.print("Enter location keyword: ");
        String keyword = scanner.nextLine();

        try {
            List<Venue> venues = venueService.searchVenuesByLocation(keyword);
            System.out.println("\nVenues matching '" + keyword + "':");
            venues.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkVenueAvailability() {
        System.out.print("Enter venue ID: ");
        String venueId = scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        try {
            boolean available = venueService.isVenueAvailable(venueId, date);
            System.out.println(available ? "Venue is available!" : "Venue is not available!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void getAverageCapacity() {
        try {
            double average = venueService.getAverageVenueCapacity();
            System.out.println("Average venue capacity: " + String.format("%.2f", average));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showUserMenu() {
        while (true) {
            System.out.println("\nUSER MANAGEMENT");
            System.out.println("1. Register New User");
            System.out.println("2. List All Users");
            System.out.println("3. Find User by ID");
            System.out.println("4. Find User by Email");
            System.out.println("5. Update User");
            System.out.println("6. Delete User");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> registerUser();
                    case 2 -> listAllUsers();
                    case 3 -> findUserById();
                    case 4 -> findUserByEmail();
                    case 5 -> updateUser();
                    case 6 -> deleteUser();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static void registerUser() {
        System.out.println("\nREGISTER NEW USER");
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Select user type:");
        System.out.println("1. Participant");
        System.out.println("2. Moderator");
        System.out.println("3. Admin");
        System.out.print("Choose: ");

        try {
            int type = Integer.parseInt(scanner.nextLine());
            User user = null;

            switch (type) {
                case 1 -> {

                    user = new Participant(id, name, email);
                }
                case 2 -> {

                    user = new Moderator(id, name, email);
                }
                case 3 -> {
                    user = new Admin(id, name, email);
                }
                default -> {
                    System.out.println("Invalid user type!");
                    return;
                }
            }

            userService.registerUser(user);
            System.out.println("User registered successfully!");

        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    private static void listAllUsers() {
        System.out.println("\nALL USERS:");
        try {
            List<User> users = userService.listUsers();
            if (users.isEmpty()) {
                System.out.println("No users found.");
            } else {
                users.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error listing users: " + e.getMessage());
        }
    }

    private static void findUserById() {
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();

        try {
            User user = userService.findUserById(id);
            if (user != null) {
                System.out.println("Found: " + user);
            } else {
                System.out.println("User not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findUserByEmail() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            User user = userService.findUserByEmail(email);
            if (user != null) {
                System.out.println("Found: " + user);
            } else {
                System.out.println("User not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateUser() {
        System.out.print("Enter user ID to update: ");
        String id = scanner.nextLine();

        try {
            User existingUser = userService.findUserById(id);
            if (existingUser == null) {
                System.out.println("User not found!");
                return;
            }

            System.out.println("Current user: " + existingUser);
            System.out.print("Enter new name (press Enter to keep current): ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = existingUser.getName();

            System.out.print("Enter new email (press Enter to keep current): ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) email = existingUser.getEmail();

            User updatedUser = null;
            if (existingUser instanceof Participant) {
                updatedUser = new Participant(id, name, email);
            } else if (existingUser instanceof Moderator) {
                updatedUser = new Moderator(id, name, email);
            } else if (existingUser instanceof Admin) {
                updatedUser = new Admin(id, name, email);
            }

            if (updatedUser != null) {
                userService.updateUser(updatedUser);
                System.out.println("User updated successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        String id = scanner.nextLine();

        try {
            User user = userService.findUserById(id);
            if (user == null) {
                System.out.println("User not found!");
                return;
            }

            System.out.println("Are you sure you want to delete: " + user.getName() + "? (yes/no)");
            String confirm = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirm)) {
                userService.deleteUser(id);
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    private static void showEventMenu() {
        while (true) {
            System.out.println("\nEVENT MANAGEMENT");
            System.out.println("1. Create New Event");
            System.out.println("2. List All Events");
            System.out.println("3. Find Event by ID");
            System.out.println("4. Search Events by Category");
            System.out.println("5. Update Event");
            System.out.println("6. Delete Event");
            System.out.println("7. List Event Attendees");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> createEvent();
                    case 2 -> listAllEvents();
                    case 3 -> findEventById();
                    case 4 -> searchEventsByCategory();
                    case 5 -> updateEvent();
                    case 6 -> deleteEvent();
                    case 7 -> listEventAttendees();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static void createEvent() {
        System.out.println("\nCREATE NEW EVENT");
        System.out.print("Enter event ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter event name: ");
        String name = scanner.nextLine();

        System.out.println("Select category:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Choose: ");

        try {
            int categoryChoice = Integer.parseInt(scanner.nextLine()) - 1;
            if (categoryChoice < 0 || categoryChoice >= categories.length) {
                System.out.println("Invalid category!");
                return;
            }
            Category category = categories[categoryChoice];

            System.out.print("Enter venue ID: ");
            String venueId = scanner.nextLine();
            Venue venue = venueService.findVenueById(venueId);
            if (venue == null) {
                System.out.println("Venue not found!");
                return;
            }

            System.out.print("Enter event date and time (YYYY-MM-DD HH:MM): ");
            String dateTimeStr = scanner.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, dateFormatter);

            System.out.print("Enter ticket price: ");
            double price = Double.parseDouble(scanner.nextLine());

            Event event = new Event(id, name, category, venue, dateTime, price);
            eventService.createEvent(event);
            System.out.println("Event created successfully!");

        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    private static void listAllEvents() {
        System.out.println("\nALL EVENTS:");
        try {
            List<Event> events = eventService.listEvents();
            if (events.isEmpty()) {
                System.out.println("No events found.");
            } else {
                events.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error listing events: " + e.getMessage());
        }
    }

    private static void findEventById() {
        System.out.print("Enter event ID: ");
        String id = scanner.nextLine();

        try {
            Event event = eventService.findEventById(id);
            if (event != null) {
                System.out.println("Found: " + event);
            } else {
                System.out.println("Event not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchEventsByCategory() {
        System.out.println("Select category:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Choose: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= categories.length) {
                System.out.println("Invalid category!");
                return;
            }

            Category category = categories[choice];
            List<Event> events = eventService.searchEventsByCategory(category);
            System.out.println("\nEvents in category " + category + ":");
            events.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateEvent() {
        System.out.print("Enter event ID to update: ");
        String id = scanner.nextLine();

        try {
            Event existingEvent = eventService.findEventById(id);
            if (existingEvent == null) {
                System.out.println("Event not found!");
                return;
            }

            System.out.println("Current event: " + existingEvent);
            System.out.print("Enter new name (press Enter to keep current): ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = existingEvent.getName();

            System.out.print("Enter new price (press Enter to keep current): ");
            String priceStr = scanner.nextLine();
            double price = priceStr.trim().isEmpty() ? existingEvent.getPrice() : Double.parseDouble(priceStr);

            Event updatedEvent = new Event(id, name, existingEvent.getCategory(),
                    existingEvent.getVenue(), existingEvent.getStartTime(), price);
            eventService.updateEvent(updatedEvent);
            System.out.println("Event updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }

    private static void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        String id = scanner.nextLine();

        try {
            Event event = eventService.findEventById(id);
            if (event == null) {
                System.out.println("Event not found!");
                return;
            }

            System.out.println("Are you sure you want to delete: " + event.getName() + "? (yes/no)");
            String confirm = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirm)) {
                eventService.deleteEvent(id);
                System.out.println("Event deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting event: " + e.getMessage());
        }
    }


    private static void listEventAttendees() {
        System.out.print("Enter event ID: ");
        String eventId = scanner.nextLine();

        try {
            List<String> attendees = eventService.listAttendees(eventId);
            System.out.println("\nAttendees for event " + eventId + ":");
            if (attendees.isEmpty()) {
                System.out.println("No attendees found.");
            } else {
                attendees.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showTicketMenu() {
        while (true) {
            System.out.println("\nTICKET MANAGEMENT");
            System.out.println("1. Buy Ticket");
            System.out.println("2. Create Custom Ticket");
            System.out.println("3. Find Ticket by ID");
            System.out.println("4. List Tickets for User");
            System.out.println("5. List Tickets for Event");
            System.out.println("6. List All Tickets");
            System.out.println("7. Update Ticket");
            System.out.println("8. Cancel Ticket");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> buyTicket();
                    case 2 -> createCustomTicket();
                    case 3 -> findTicketById();
                    case 4 -> listTicketsForUser();
                    case 5 -> listTicketsForEvent();
                    case 6 -> listAllTickets();
                    case 7 -> updateTicket();
                    case 8 -> cancelTicket();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static void buyTicket() {
        System.out.print("Enter event ID: ");
        String eventId = scanner.nextLine();

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        try {
            Ticket ticket = ticketService.buyTicket(eventId, userId);
            System.out.println("Ticket purchased: " + ticket);
        } catch (Exception e) {
            System.out.println("Error buying ticket: " + e.getMessage());
        }
    }

    private static void createCustomTicket() {
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter event ID: ");
        String eventId = scanner.nextLine();

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        try {
            Ticket ticket = new Ticket(id, eventId, userId, LocalDateTime.now(), price, "ACTIVE");
            ticketService.createTicket(ticket);
            System.out.println("Custom ticket created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating ticket: " + e.getMessage());
        }
    }

    private static void findTicketById() {
        System.out.print("Enter ticket ID: ");
        String id = scanner.nextLine();

        try {
            Ticket ticket = ticketService.findTicketById(id);
            if (ticket != null) {
                System.out.println("Found: " + ticket);
            } else {
                System.out.println("Ticket not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listTicketsForUser() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        try {
            List<Ticket> tickets = ticketService.listTicketsForUser(userId);
            System.out.println("\nTickets for user " + userId + ":");
            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
            } else {
                tickets.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listTicketsForEvent() {
        System.out.print("Enter event ID: ");
        String eventId = scanner.nextLine();

        try {
            List<Ticket> tickets = ticketService.listTicketsForEvent(eventId);
            System.out.println("\nTickets for event " + eventId + ":");
            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
            } else {
                tickets.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAllTickets() {
        System.out.println("\nALL TICKETS:");
        try {
            List<Ticket> tickets = ticketService.listAllTickets();
            if (tickets.isEmpty()) {
                System.out.println("No tickets found.");
            } else {
                tickets.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error listing tickets: " + e.getMessage());
        }
    }

    private static void updateTicket() {
        System.out.print("Enter ticket ID to update: ");
        String id = scanner.nextLine();

        try {
            Ticket existingTicket = ticketService.findTicketById(id);
            if (existingTicket == null) {
                System.out.println("Ticket not found!");
                return;
            }

            System.out.println("Current ticket: " + existingTicket);
            System.out.print("Enter new price (press Enter to keep current): ");
            String priceStr = scanner.nextLine();
            double price = priceStr.trim().isEmpty() ? existingTicket.getPrice() : Double.parseDouble(priceStr);

            System.out.print("Enter new status (ACTIVE/CANCELLED/USED, press Enter to keep current): ");
            String status = scanner.nextLine();
            if (status.trim().isEmpty()) status = existingTicket.getStatus();

            Ticket updatedTicket = new Ticket(id, existingTicket.getEventId(), existingTicket.getUserId(),
                    existingTicket.getPurchaseDate(), price, status);
            ticketService.updateTicket(updatedTicket);
            System.out.println("Ticket updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating ticket: " + e.getMessage());
        }
    }

    private static void cancelTicket() {
        System.out.print("Enter ticket ID to cancel: ");
        String id = scanner.nextLine();

        try {
            Ticket ticket = ticketService.findTicketById(id);
            if (ticket == null) {
                System.out.println("Ticket not found!");
                return;
            }

            System.out.println("Current ticket: " + ticket);
            System.out.println("Are you sure you want to cancel this ticket? (yes/no)");
            String confirm = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirm)) {
                ticketService.cancelTicket(id);
                System.out.println("Ticket cancelled successfully!");
            } else {
                System.out.println("Cancel operation cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error cancelling ticket: " + e.getMessage());
        }
    }

    private static void deleteTicket() {
        System.out.print("Enter ticket ID to delete: ");
        String id = scanner.nextLine();

        try {
            Ticket ticket = ticketService.findTicketById(id);
            if (ticket == null) {
                System.out.println("Ticket not found!");
                return;
            }

            System.out.println("Current ticket: " + ticket);
            System.out.println("Are you sure you want to permanently delete this ticket? (yes/no)");
            String confirm = scanner.nextLine();

            if ("yes".equalsIgnoreCase(confirm)) {
                ticketService.deleteTicket(id);
                System.out.println("Ticket deleted successfully!");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting ticket: " + e.getMessage());
        }
    }
}