import java.util.*;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public void registerUser(User u) {
        if (users.containsKey(u.getId())) {
            throw new IllegalArgumentException("User existent: " + u.getId());
        }
        users.put(u.getId(), u);
    }

    public User findById(String userId) {
        return users.get(userId);
    }

    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }
}
