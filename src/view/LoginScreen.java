import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    private final AuthController authController;

    public LoginScreen(AppFrame app, AuthController authController) {
        this.authController = authController;

        setLayout(new GridLayout(6, 1));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton createBtn = new JButton("Create Account");

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            if (authController.login(username, password)) {
                app.setCurrentUser(username);
                JOptionPane.showMessageDialog(this, "Welcome, " + username + "!");
                app.showScreen(AppFrame.MAIN_MENU);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        createBtn.addActionListener(e -> app.showScreen(AppFrame.CREATE_ACCOUNT));

        add(new JLabel("Username:"));
        add(userField);
        add(new JLabel("Password:"));
        add(passField);
        add(loginBtn);
        add(createBtn);
    }
}
