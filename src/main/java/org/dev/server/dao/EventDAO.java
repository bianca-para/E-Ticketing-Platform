package org.dev.server.dao;

import org.dev.server.models.Event;
import org.dev.server.models.Category;
import org.dev.server.models.Venue;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EventDAO extends GenericDAOImpl<Event> {
    private static EventDAO instance;

    private EventDAO() {}

    public static EventDAO getInstance() {
        if (instance == null) {
            synchronized (EventDAO.class) {
                if (instance == null) {
                    instance = new EventDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Event event) {
        String sql = "INSERT INTO events (id, name, category, venue_id, event_date, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getId());
            stmt.setString(2, event.getName());
            stmt.setString(3, event.getCategory().toString());
            stmt.setString(4, event.getVenue().getId());
            stmt.setTimestamp(5, Timestamp.valueOf(event.getStartTime()));
            stmt.setDouble(6, event.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public Event read(String id) {
        String sql = "SELECT e.*, v.name as venue_name, v.address, v.capacity " +
                "FROM events e JOIN venues v ON e.venue_id = v.id WHERE e.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEvent(rs);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE events SET name = ?, category = ?, venue_id = ?, event_date = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getCategory().toString());
            stmt.setString(3, event.getVenue().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(event.getStartTime()));
            stmt.setDouble(5, event.getPrice());
            stmt.setString(6, event.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM events WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, v.name as venue_name, v.address, v.capacity " +
                "FROM events e JOIN venues v ON e.venue_id = v.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return events;
    }

    public List<Event> findByCategory(Category category) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, v.name as venue_name, v.address, v.capacity " +
                "FROM events e JOIN venues v ON e.venue_id = v.id WHERE e.category = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return events;
    }

    private Event mapResultSetToEvent(ResultSet rs) throws SQLException {
        Venue venue = new Venue(
                rs.getString("venue_id"),
                rs.getString("venue_name"),
                rs.getString("address"),
                rs.getInt("capacity")
        );

        return new Event(
                rs.getString("id"),
                rs.getString("name"),
                Category.valueOf(rs.getString("category")),
                venue,
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getDouble("price")
        );
    }

    public void registerAttendee(String eventId, String userId) {
        String sql = "INSERT INTO event_attendees (event_id, user_id) VALUES (?, ?) ON CONFLICT (event_id, user_id) DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            stmt.setString(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public List<String> getAttendees(String eventId) {
        List<String> attendees = new ArrayList<>();
        String sql = """
        SELECT u.name 
        FROM event_attendees ea 
        JOIN users u ON ea.user_id = u.id 
        WHERE ea.event_id = ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attendees.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return attendees;
    }
}