import javax.swing.*;

public class BodyInfoScreen extends JPanel {

    public BodyInfoScreen(AppFrame app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField age = new JTextField();
        JTextField height = new JTextField();
        JTextField weight = new JTextField();

        JButton save = new JButton("Save Body Info");

        save.addActionListener(e -> {
            int a = Integer.parseInt(age.getText());
            int h = Integer.parseInt(height.getText());
            int w = Integer.parseInt(weight.getText());

            if (a <= 0 || h <= 0 || w <= 0) {
                JOptionPane.showMessageDialog(this, "Values must be positive.");
                return;
            }

            BodyInfoDatabase.save(a,h,w);
            JOptionPane.showMessageDialog(this, "Body information saved.");
            app.showScreen(AppFrame.MAIN_MENU);
        });

        add(new JLabel("Age:"));
        add(age);
        add(new JLabel("Height:"));
        add(height);
        add(new JLabel("Weight:"));
        add(weight);
        add(save);
    }
}
