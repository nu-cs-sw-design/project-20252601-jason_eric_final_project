import javax.swing.*;

public class BodyInfoScreen extends JPanel {

    private final BodyInfoController bodyInfoController;

    public BodyInfoScreen(AppFrame app, BodyInfoController bodyInfoController) {
        this.bodyInfoController = bodyInfoController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField age = new JTextField();
        JTextField height = new JTextField();
        JTextField weight = new JTextField();
        JComboBox<String> sexField = new JComboBox<>(new String[]{"Female", "Male", "Non-binary", "Prefer not to say"});
        JTextArea latest = new JTextArea(3, 20);
        latest.setEditable(false);

        JButton save = new JButton("Save Body Info");

        save.addActionListener(e -> {
            if (!app.ensureLoggedIn()) {
                return;
            }
            try {
                int a = Integer.parseInt(age.getText().trim());
                int h = Integer.parseInt(height.getText().trim());
                int w = Integer.parseInt(weight.getText().trim());
                String sex = (String) sexField.getSelectedItem();

                if (a <= 0 || h <= 0 || w <= 0) {
                    JOptionPane.showMessageDialog(this, "Values must be positive.");
                    return;
                }

                bodyInfoController.save(app.getCurrentUser(), new BodyInfo(a, h, w, sex));
                latest.setText("Age: " + a + "\nSex: " + sex + "\nHeight: " + h + " in\nWeight: " + w + " lbs");
                JOptionPane.showMessageDialog(this, "Body information saved.");
                app.showScreen(AppFrame.MAIN_MENU);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please provide valid numbers.");
            }
        });

        JButton refresh = new JButton("Load Latest");
        refresh.addActionListener(e -> {
            if (app.ensureLoggedIn()) {
                bodyInfoController.fetch(app.getCurrentUser())
                        .ifPresentOrElse(info -> latest.setText(
                                "Age: " + info.getAge() +
                                        "\nSex: " + info.getSex() +
                                        "\nHeight: " + info.getHeightInches() + " in" +
                                        "\nWeight: " + info.getWeightPounds() + " lbs"),
                                () -> latest.setText("No body info saved yet."));
            }
        });

        add(new JLabel("Age:"));
        add(age);
        add(new JLabel("Sex:"));
        add(sexField);
        add(new JLabel("Height (inches):"));
        add(height);
        add(new JLabel("Weight (lbs):"));
        add(weight);
        add(save);
        add(refresh);
        add(new JScrollPane(latest));

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> app.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
