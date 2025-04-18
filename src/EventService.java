import java.util.*;

public class EventService {
    private SortedSet<Event> events = new TreeSet<>();
    private Map<String, List<String>> attendees = new HashMap<>();

    public void createEvent(Event e) {
        if (!events.add(e)) {
            throw new IllegalArgumentException("Eveniment deja existent: " + e.getId());
        }
        attendees.put(e.getId(), new ArrayList<>());
    }

    public List<Event> listEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> searchByCategory(Category c) {
        List<Event> rez = new ArrayList<>();
        for (Event e : events) {
            if (e.getCategory() == c) rez.add(e);
        }
        return rez;
    }

    public void registerAttendee(String eventId, String userId) {
        List<String> list = attendees.get(eventId);
        if (list == null) throw new NoSuchElementException("Eveniment inexistent: " + eventId);
        if (!list.contains(userId)) list.add(userId);
    }

    public List<String> listAttendees(String eventId) {
        return Collections.unmodifiableList(
                attendees.getOrDefault(eventId, Collections.emptyList())
        );
    }

    public void updateEvent(Event updated) {
        events.removeIf(e -> e.getId().equals(updated.getId()));
        events.add(updated);
    }
}
