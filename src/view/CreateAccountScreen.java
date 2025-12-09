import javax.swing.*;

public class CreateAccountScreen extends JPanel {

    private final AuthController authController;

    public CreateAccountScreen(NavigationContext navigationContext, AuthController authController) {
        this.authController = authController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton createBtn = new JButton("Create Account");

        createBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password are required.");
                return;
            }

            if (authController.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Account created! Please log in.");
                navigationContext.showScreen(AppFrame.LOGIN);
            } else {
                JOptionPane.showMessageDialog(this, "Username already taken.");
            }
        });

        add(new JLabel("Enter Username:"));
        add(userField);
        add(new JLabel("Enter Password:"));
        add(passField);
        add(createBtn);
    }
}
