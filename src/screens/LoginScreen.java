import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    public LoginScreen(AppFrame app) {
        setLayout(new GridLayout(5,1));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton createBtn = new JButton("Create Account");

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if (UserDatabase.validateLogin(u, p)) {
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
