package org.dev.server.dao;

import org.dev.server.models.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TicketDAO extends GenericDAOImpl<Ticket> {
    private static TicketDAO instance;

    private TicketDAO() {}

    public static TicketDAO getInstance() {
        if (instance == null) {
            synchronized (TicketDAO.class) {
                if (instance == null) {
                    instance = new TicketDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Ticket ticket) {
        String sql = "INSERT INTO tickets (id, event_id, user_id, purchase_date, price, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getId());
            stmt.setString(2, ticket.getEventId());
            stmt.setString(3, ticket.getUserId());
            stmt.setTimestamp(4, Timestamp.valueOf(ticket.getPurchaseDate()));
            stmt.setDouble(5, ticket.getPrice());
            stmt.setString(6, ticket.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public Ticket read(String id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE tickets SET event_id = ?, user_id = ?, purchase_date = ?, price = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getEventId());
            stmt.setString(2, ticket.getUserId());
            stmt.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseDate()));
            stmt.setDouble(4, ticket.getPrice());
            stmt.setString(5, ticket.getStatus());
            stmt.setString(6, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets ORDER BY purchase_date DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tickets;
    }

    public List<Ticket> findByUserId(String userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ? ORDER BY purchase_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tickets;
    }

    public List<Ticket> findByEventId(String eventId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE event_id = ? ORDER BY purchase_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tickets;
    }

    public List<Ticket> findByStatus(String status) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE status = ? ORDER BY purchase_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("id"),
                        rs.getString("event_id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tickets;
    }

    public Ticket createTicket(String eventId, String userId) {
        double price = getEventPrice(eventId);

        Ticket ticket = new Ticket(
                generateTicketId(),
                eventId,
                userId,
                LocalDateTime.now(),
                price,
                "ACTIVE"
        );

        create(ticket);
        return ticket;
    }

    public void cancelTicket(String ticketId) {
        String sql = "UPDATE tickets SET status = 'CANCELLED' WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticketId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }


    private double getEventPrice(String eventId) {
        String sql = "SELECT price FROM events WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return 0.0;
    }

    private String generateTicketId() {
        return "T" + System.currentTimeMillis();
    }
}