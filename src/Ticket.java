import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private String eventId;
    private String userId;
    private LocalDateTime purchaseTime;

    public Ticket(String id, String eventId, String userId, LocalDateTime purchaseTime) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.purchaseTime = purchaseTime;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", userId='" + userId + '\'' +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
