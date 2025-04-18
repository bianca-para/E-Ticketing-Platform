import java.time.LocalDateTime;

public class Event implements Comparable<Event> {
    private String id;
    private String name;
    private Category category;
    private Venue venue;
    private LocalDateTime startTime;
    private double price;

    public Event(String id, String name, Category category, Venue venue, LocalDateTime startTime, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.venue = venue;
        this.startTime = startTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", venue=" + venue +
                ", startTime=" + startTime +
                ", price=" + price +
                '}';
    }
}
