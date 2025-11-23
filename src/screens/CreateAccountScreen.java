import javax.swing.*;

public class CreateAccountScreen extends JPanel {

    public CreateAccountScreen(AppFrame app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton createBtn = new JButton("Create Account");

        createBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if (UserDatabase.userExists(u)) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            } else {
                UserDatabase.addUser(u, p);
                JOptionPane.showMessageDialog(this, "Account created!");
                app.showScreen(AppFrame.LOGIN);
            }
        });

        add(new JLabel("Enter Username:"));
        add(userField);
        add(new JLabel("Enter Password:"));
        add(passField);
        add(createBtn);
    }
}
