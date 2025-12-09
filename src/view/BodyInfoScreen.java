import javax.swing.*;

public class BodyInfoScreen extends JPanel {

    private final BodyInfoController bodyInfoController;
    private final NavigationContext navigationContext;

    public BodyInfoScreen(NavigationContext navigationContext, BodyInfoController bodyInfoController) {
        this.navigationContext = navigationContext;
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
            if (!navigationContext.ensureLoggedIn()) {
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

                bodyInfoController.save(navigationContext.getCurrentUser(), new BodyInfo(a, h, w, sex));
                latest.setText("Age: " + a + "\nSex: " + sex + "\nHeight: " + h + " in\nWeight: " + w + " lbs");
                JOptionPane.showMessageDialog(this, "Body information saved.");
                navigationContext.showScreen(AppFrame.MAIN_MENU);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please provide valid numbers.");
            }
        });

        JButton refresh = new JButton("Load Latest");
        refresh.addActionListener(e -> {
            if (navigationContext.ensureLoggedIn()) {
                bodyInfoController.fetch(navigationContext.getCurrentUser())
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
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
