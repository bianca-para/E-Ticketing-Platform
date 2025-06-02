package org.dev.server.services;

import org.dev.server.dao.TicketDAO;
import org.dev.server.models.Ticket;
import java.util.List;

public class TicketService {
    private static TicketService instance;
    private TicketDAO ticketDAO;
    private AuditService auditService;

    private TicketService() {
        this.ticketDAO = TicketDAO.getInstance();
        this.auditService = AuditService.getInstance();
    }

    public static TicketService getInstance() {
        if (instance == null) {
            synchronized (TicketService.class) {
                if (instance == null) {
                    instance = new TicketService();
                }
            }
        }
        return instance;
    }

    // CREATE
    public Ticket buyTicket(String eventId, String userId) {
        try {
            Ticket ticket = ticketDAO.createTicket(eventId, userId);
            auditService.logActionWithDetails("buyTicket", "Ticket ID: " + ticket.getId() + ", Event ID: " + eventId + ", User ID: " + userId);
            System.out.println("Ticket purchased: " + ticket.getId() + " for event " + eventId);
            return ticket;
        } catch (Exception e) {
            auditService.logActionWithDetails("buyTicket_FAILED", "Event ID: " + eventId + ", User ID: " + userId + ", Error: " + e.getMessage());
            System.err.println("Failed to buy ticket: " + e.getMessage());
            throw e;
        }
    }

    public void createTicket(Ticket ticket) {
        try {
            ticketDAO.create(ticket);
            auditService.logActionWithDetails("createTicket", "Ticket ID: " + ticket.getId() + ", Event ID: " + ticket.getEventId());
            System.out.println("Ticket created: " + ticket.getId());
        } catch (Exception e) {
            auditService.logActionWithDetails("createTicket_FAILED", "Ticket ID: " + ticket.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to create ticket: " + e.getMessage());
            throw e;
        }
    }

    // READ
    public Ticket findTicketById(String ticketId) {
        try {
            Ticket ticket = ticketDAO.read(ticketId);
            auditService.logActionWithDetails("findTicketById", "Ticket ID: " + ticketId + (ticket != null ? ", Found" : ", Not found"));
            return ticket;
        } catch (Exception e) {
            auditService.logActionWithDetails("findTicketById_FAILED", "Ticket ID: " + ticketId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Ticket> listTicketsForUser(String userId) {
        try {
            List<Ticket> tickets = ticketDAO.findByUserId(userId);
            auditService.logActionWithDetails("listTicketsForUser", "User ID: " + userId + ", Found: " + tickets.size() + " tickets");
            return tickets;
        } catch (Exception e) {
            auditService.logActionWithDetails("listTicketsForUser_FAILED", "User ID: " + userId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Ticket> listTicketsForEvent(String eventId) {
        try {
            List<Ticket> tickets = ticketDAO.findByEventId(eventId);
            auditService.logActionWithDetails("listTicketsForEvent", "Event ID: " + eventId + ", Found: " + tickets.size() + " tickets");
            return tickets;
        } catch (Exception e) {
            auditService.logActionWithDetails("listTicketsForEvent_FAILED", "Event ID: " + eventId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Ticket> listAllTickets() {
        try {
            List<Ticket> tickets = ticketDAO.findAll();
            auditService.logActionWithDetails("listAllTickets", "Found " + tickets.size() + " tickets");
            return tickets;
        } catch (Exception e) {
            auditService.logActionWithDetails("listAllTickets_FAILED", "Error: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE
    public void updateTicket(Ticket ticket) {
        try {
            ticketDAO.update(ticket);
            auditService.logActionWithDetails("updateTicket", "Ticket ID: " + ticket.getId() + ", Status: " + ticket.getStatus());
            System.out.println("Ticket updated: " + ticket.getId());
        } catch (Exception e) {
            auditService.logActionWithDetails("updateTicket_FAILED", "Ticket ID: " + ticket.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to update ticket: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void cancelTicket(String ticketId) {
        try {
            ticketDAO.cancelTicket(ticketId);
            auditService.logActionWithDetails("cancelTicket", "Ticket ID: " + ticketId);
            System.out.println("Ticket cancelled: " + ticketId);
        } catch (Exception e) {
            auditService.logActionWithDetails("cancelTicket_FAILED", "Ticket ID: " + ticketId + ", Error: " + e.getMessage());
            System.err.println("Failed to cancel ticket: " + e.getMessage());
            throw e;
        }
    }

    public void deleteTicket(String ticketId) {
        try {
            ticketDAO.delete(ticketId);
            auditService.logActionWithDetails("deleteTicket", "Ticket ID: " + ticketId);
            System.out.println("Ticket deleted: " + ticketId);
        } catch (Exception e) {
            auditService.logActionWithDetails("deleteTicket_FAILED", "Ticket ID: " + ticketId + ", Error: " + e.getMessage());
            System.err.println("Failed to delete ticket: " + e.getMessage());
            throw e;
        }
    }


}