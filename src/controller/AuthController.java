public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String username, String password) {
        return userRepository.addUser(username.trim(), password);
    }

    public boolean login(String username, String password) {
        return userRepository.validateLogin(username.trim(), password);
    }
}
