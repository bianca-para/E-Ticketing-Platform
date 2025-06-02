package org.dev.server.models;

import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private String eventId;
    private String userId;
    private LocalDateTime purchaseDate;
    private double price;
    private String status;

    //constr pt dao
    public Ticket(String id, String eventId, String userId, LocalDateTime purchaseDate, double price, String status) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = status;
    }

    //constr simplu
    public Ticket(String id, String eventId, String userId, LocalDateTime purchaseDate) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.price = 0.0;
        this.status = "ACTIVE";
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", userId='" + userId + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}