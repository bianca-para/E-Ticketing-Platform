import java.util.*;

public class Moderator extends User {
    private Set<String> assignedEventIds = new HashSet<>();
    private List<String> permissions        = new ArrayList<>();
    private int reportsHandledCount         = 0;
    private boolean active                  = true;

    public Moderator(String id, String name, String email) {
        super(id, name, email);
    }

    public Set<String> getAssignedEventIds() {
        return assignedEventIds;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public int getReportsHandledCount() {
        return reportsHandledCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setAssignedEventIds(Set<String> assignedEventIds) {
        this.assignedEventIds = assignedEventIds;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setReportsHandledCount(int reportsHandledCount) {
        this.reportsHandledCount = reportsHandledCount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void assignEvent(String eventId) {
        assignedEventIds.add(eventId);
    }

    public void grantPermission(String permission) {
        permissions.add(permission);
    }

    public void handleReport() {
        reportsHandledCount++;
    }

    @Override
    public String toString() {
        return "Moderator{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", assignedEventIds=" + assignedEventIds +
                ", permissions=" + permissions +
                ", reportsHandledCount=" + reportsHandledCount +
                ", active=" + active +
                '}';
    }
}
