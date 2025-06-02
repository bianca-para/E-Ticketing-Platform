package org.dev.server.services;

import org.dev.server.dao.VenueDAO;
import org.dev.server.models.Venue;
import java.util.List;

public class VenueService {
    private static VenueService instance;
    private VenueDAO venueDAO;
    private AuditService auditService;

    private VenueService() {
        this.venueDAO = VenueDAO.getInstance();
        this.auditService = AuditService.getInstance();
    }

    public static VenueService getInstance() {
        if (instance == null) {
            synchronized (VenueService.class) {
                if (instance == null) {
                    instance = new VenueService();
                }
            }
        }
        return instance;
    }

    public void createVenue(Venue venue) {
        try {
            venueDAO.create(venue);
            auditService.logActionWithDetails("createVenue", "Venue ID: " + venue.getId() + ", Name: " + venue.getName() + ", Capacity: " + venue.getCapacity());
            System.out.println("Venue created: " + venue.getName() + " (Capacity: " + venue.getCapacity() + ")");
        } catch (Exception e) {
            auditService.logActionWithDetails("createVenue_FAILED", "Venue ID: " + venue.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to create venue: " + e.getMessage());
            throw e;
        }
    }

    // READ
    public Venue findVenueById(String venueId) {
        try {
            Venue venue = venueDAO.read(venueId);
            auditService.logActionWithDetails("findVenueById", "Venue ID: " + venueId + (venue != null ? ", Found: " + venue.getName() : ", Not found"));
            return venue;
        } catch (Exception e) {
            auditService.logActionWithDetails("findVenueById_FAILED", "Venue ID: " + venueId + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Venue> listVenues() {
        try {
            List<Venue> venues = venueDAO.findAll();
            auditService.logActionWithDetails("listVenues", "Found " + venues.size() + " venues");
            return venues;
        } catch (Exception e) {
            auditService.logActionWithDetails("listVenues_FAILED", "Error: " + e.getMessage());
            throw e;
        }
    }

    public Venue findVenueByName(String name) {
        try {
            Venue venue = venueDAO.findByName(name);
            auditService.logActionWithDetails("findVenueByName", "Name: " + name + (venue != null ? ", Found: " + venue.getId() : ", Not found"));
            return venue;
        } catch (Exception e) {
            auditService.logActionWithDetails("findVenueByName_FAILED", "Name: " + name + ", Error: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE
    public void updateVenue(Venue venue) {
        try {
            venueDAO.update(venue);
            auditService.logActionWithDetails("updateVenue", "Venue ID: " + venue.getId() + ", Name: " + venue.getName());
            System.out.println("Venue updated: " + venue.getName());
        } catch (Exception e) {
            auditService.logActionWithDetails("updateVenue_FAILED", "Venue ID: " + venue.getId() + ", Error: " + e.getMessage());
            System.err.println("Failed to update venue: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void deleteVenue(String venueId) {
        try {
            // Verifică dacă venue-ul are evenimente asociate
            if (hasActiveEvents(venueId)) {
                throw new IllegalStateException("Cannot delete venue with active events");
            }

            venueDAO.delete(venueId);
            auditService.logActionWithDetails("deleteVenue", "Venue ID: " + venueId);
            System.out.println("Venue deleted: " + venueId);
        } catch (Exception e) {
            auditService.logActionWithDetails("deleteVenue_FAILED", "Venue ID: " + venueId + ", Error: " + e.getMessage());
            System.err.println("Failed to delete venue: " + e.getMessage());
            throw e;
        }
    }

    // BUSINESS LOGIC
    public List<Venue> getVenuesByCapacity(int minCapacity, int maxCapacity) {
        try {
            List<Venue> venues = venueDAO.findByCapacityRange(minCapacity, maxCapacity);
            auditService.logActionWithDetails("getVenuesByCapacity", "Min: " + minCapacity + ", Max: " + maxCapacity + ", Found: " + venues.size() + " venues");
            return venues;
        } catch (Exception e) {
            auditService.logActionWithDetails("getVenuesByCapacity_FAILED", "Min: " + minCapacity + ", Max: " + maxCapacity + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Venue> getLargeVenues(int minimumCapacity) {
        try {
            List<Venue> venues = venueDAO.findByMinimumCapacity(minimumCapacity);
            auditService.logActionWithDetails("getLargeVenues", "Min capacity: " + minimumCapacity + ", Found: " + venues.size() + " venues");
            return venues;
        } catch (Exception e) {
            auditService.logActionWithDetails("getLargeVenues_FAILED", "Min capacity: " + minimumCapacity + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Venue> searchVenuesByLocation(String locationKeyword) {
        try {
            List<Venue> venues = venueDAO.findByLocationKeyword(locationKeyword);
            auditService.logActionWithDetails("searchVenuesByLocation", "Keyword: " + locationKeyword + ", Found: " + venues.size() + " venues");
            return venues;
        } catch (Exception e) {
            auditService.logActionWithDetails("searchVenuesByLocation_FAILED", "Keyword: " + locationKeyword + ", Error: " + e.getMessage());
            throw e;
        }
    }

    public boolean isVenueAvailable(String venueId, String date) {
        try {
            boolean available = venueDAO.isAvailableOnDate(venueId, date);
            auditService.logActionWithDetails("isVenueAvailable", "Venue ID: " + venueId + ", Date: " + date + ", Available: " + available);
            return available;
        } catch (Exception e) {
            auditService.logActionWithDetails("isVenueAvailable_FAILED", "Venue ID: " + venueId + ", Date: " + date + ", Error: " + e.getMessage());
            throw e;
        }
    }

    private boolean hasActiveEvents(String venueId) {
        try {
            return venueDAO.hasActiveEvents(venueId);
        } catch (Exception e) {
            return false; // În caz de eroare, presupunem că nu are evenimente
        }
    }

    public double getAverageVenueCapacity() {
        try {
            double average = venueDAO.calculateAverageCapacity();
            auditService.logActionWithDetails("getAverageVenueCapacity", "Average capacity: " + average);
            return average;
        } catch (Exception e) {
            auditService.logActionWithDetails("getAverageVenueCapacity_FAILED", "Error: " + e.getMessage());
            throw e;
        }
    }
}