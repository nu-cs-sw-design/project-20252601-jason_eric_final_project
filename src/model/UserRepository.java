import java.util.Optional;

public interface UserRepository {
    boolean addUser(String username, String password);
    boolean validateLogin(String username, String password);
    Optional<User> find(String username);
}
