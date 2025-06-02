package org.dev.server.services;

import org.dev.server.dao.EventDAO;
import org.dev.server.models.Event;
import org.dev.server.models.Category;
import java.util.List;

public class EventService {
    private static EventService instance;
    private EventDAO eventDAO;
    private AuditService auditService;

    private EventService() {
        this.eventDAO = EventDAO.getInstance();
        this.auditService = AuditService.getInstance();
    }

    public static EventService getInstance() {
        if (instance == null) {
            synchronized (EventService.class) {
                if (instance == null) {
                    instance = new EventService();
                }
            }
        }
        return instance;
    }

    // CREATE
    public void createEvent(Event event) {
        try {
            eventDAO.create(event);
            auditService.logActionWithDetails("createEvent", "Event ID: " + event.getId() + ", Name: " + event.getName());
            System.out.println("Event created: " + event.getName());
        } catch (Exception e) {
            auditService.logActionWithDetails("createEvent_FAILED", "Event ID: " + event.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to create event: " + e.getMessage());
            throw e;
        }
    }

    // READ
    public Event findEventById(String eventId) {
        try {
            Event event = eventDAO.read(eventId);
            auditService.logActionWithDetails("findEventById", "Event ID: " + eventId);
            return event;
        } catch (Exception e) {
            auditService.logActionWithDetails("findEventById_FAILED", "Event ID: " + eventId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Event> listEvents() {
        try {
            List<Event> events = eventDAO.findAll();
            auditService.logActionWithDetails("listEvents", "Found " + events.size() + " events");
            return events;
        } catch (Exception e) {
            auditService.logActionWithDetails("listEvents_FAILED", "Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Event> searchEventsByCategory(Category category) {
        try {
            List<Event> events = eventDAO.findByCategory(category);
            auditService.logActionWithDetails("searchEventsByCategory", "Category: " + category + ", Found: " + events.size() + " events");
            return events;
        } catch (Exception e) {
            auditService.logActionWithDetails("searchEventsByCategory_FAILED", "Category: " + category + ", Error: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE
    public void updateEvent(Event event) {
        try {
            eventDAO.update(event);
            auditService.logActionWithDetails("updateEvent", "Event ID: " + event.getId() + ", Name: " + event.getName());
            System.out.println("Event updated: " + event.getName());
        } catch (Exception e) {
            auditService.logActionWithDetails("updateEvent_FAILED", "Event ID: " + event.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to update event: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void deleteEvent(String eventId) {
        try {
            eventDAO.delete(eventId);
            auditService.logActionWithDetails("deleteEvent", "Event ID: " + eventId);
            System.out.println("Event deleted: " + eventId);
        } catch (Exception e) {
            auditService.logActionWithDetails("deleteEvent_FAILED", "Event ID: " + eventId + ", Error: " + e.getMessage());
            System.err.println("Failed to delete event: " + e.getMessage());
            throw e;
        }
    }

    public void registerAttendee(String eventId, String userId) {
        try {
            eventDAO.registerAttendee(eventId, userId);
            auditService.logActionWithDetails("registerAttendee", "Event ID: " + eventId + ", User ID: " + userId);
            System.out.println("User " + userId + " registered for event " + eventId);
        } catch (Exception e) {
            auditService.logActionWithDetails("registerAttendee_FAILED", "Event ID: " + eventId + ", User ID: " + userId + ", Error: " + e.getMessage());
            System.err.println("Failed to register attendee: " + e.getMessage());
            throw e;
        }
    }

    public List<String> listAttendees(String eventId) {
        try {
            List<String> attendees = eventDAO.getAttendees(eventId);
            auditService.logActionWithDetails("listAttendees", "Event ID: " + eventId + ", Found: " + attendees.size() + " attendees");
            return attendees;
        } catch (Exception e) {
            auditService.logActionWithDetails("listAttendees_FAILED", "Event ID: " + eventId + ", Error: " + e.getMessage());
            throw e;
        }
    }
}