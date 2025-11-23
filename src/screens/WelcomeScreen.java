import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JPanel {

    public WelcomeScreen(AppFrame app) {
        setLayout(new BorderLayout());

        JLabel msg = new JLabel("Welcome to the Fitness App!", SwingConstants.CENTER);
        msg.setFont(new Font("Arial", Font.BOLD, 24));

        JButton next = new JButton("Continue");
        next.addActionListener(e -> app.showScreen(AppFrame.LOGIN));

        add(msg, BorderLayout.CENTER);
        add(next, BorderLayout.SOUTH);
    }
}
