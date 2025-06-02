package org.dev.server.dao;

import org.dev.server.models.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDAO extends GenericDAOImpl<Venue> {
    private static VenueDAO instance;

    private VenueDAO() {}

    public static VenueDAO getInstance() {
        if (instance == null) {
            synchronized (VenueDAO.class) {
                if (instance == null) {
                    instance = new VenueDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Venue venue) {
        String sql = "INSERT INTO venues (id, name, address, capacity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venue.getId());
            stmt.setString(2, venue.getName());
            stmt.setString(3, venue.getAddress());
            stmt.setInt(4, venue.getCapacity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public Venue read(String id) {
        String sql = "SELECT * FROM venues WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                );
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    @Override
    public void update(Venue venue) {
        String sql = "UPDATE venues SET name = ?, address = ?, capacity = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venue.getName());
            stmt.setString(2, venue.getAddress());
            stmt.setInt(3, venue.getCapacity());
            stmt.setString(4, venue.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM venues WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Venue> findAll() {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM venues ORDER BY name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                venues.add(new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return venues;
    }

    public Venue findByName(String name) {
        String sql = "SELECT * FROM venues WHERE name ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                );
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    public List<Venue> findByCapacityRange(int minCapacity, int maxCapacity) {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM venues WHERE capacity BETWEEN ? AND ? ORDER BY capacity";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, minCapacity);
            stmt.setInt(2, maxCapacity);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                venues.add(new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return venues;
    }

    public List<Venue> findByMinimumCapacity(int minimumCapacity) {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM venues WHERE capacity >= ? ORDER BY capacity DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, minimumCapacity);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                venues.add(new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return venues;
    }

    public List<Venue> findByLocationKeyword(String keyword) {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM venues WHERE address ILIKE ? ORDER BY name";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                venues.add(new Venue(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return venues;
    }

    public boolean isAvailableOnDate(String venueId, String date) {
        String sql = "SELECT COUNT(*) FROM events WHERE venue_id = ? AND DATE(event_date) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venueId);
            stmt.setString(2, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // 0 = available, >0 = occupied
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    public boolean hasActiveEvents(String venueId) {
        String sql = "SELECT COUNT(*) FROM events WHERE venue_id = ? AND event_date > CURRENT_TIMESTAMP";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venueId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    public double calculateAverageCapacity() {
        String sql = "SELECT AVG(capacity) as avg_capacity FROM venues";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("avg_capacity");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return 0.0;
    }
}