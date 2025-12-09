import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public synchronized boolean addUser(String username, String password) {
        if (username == null || username.isBlank() || users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        return true;
    }

    @Override
    public synchronized boolean validateLogin(String username, String password) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username) &&
                        user.getPassword().equals(password));
    }

    @Override
    public synchronized Optional<User> find(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
