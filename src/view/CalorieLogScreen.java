import javax.swing.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class CalorieLogScreen extends JPanel {

    private final CalorieController calorieController;
    private final NavigationContext navigationContext;

    public CalorieLogScreen(NavigationContext navigationContext, CalorieController calorieController) {
        this.navigationContext = navigationContext;
        this.calorieController = calorieController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField("2025-01-01");
        JTextField caloriesField = new JTextField();
        JTextArea descriptionField = new JTextArea(3, 25);
        descriptionField.setBorder(BorderFactory.createEtchedBorder());
        JTextArea logView = new JTextArea(8, 25);
        logView.setEditable(false);

        JButton save = new JButton("Log Calories");
        JButton refresh = new JButton("Refresh");

        save.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            try {
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                int calories = Integer.parseInt(caloriesField.getText().trim());
                String description = descriptionField.getText().trim();
                if (calories <= 0) {
                    JOptionPane.showMessageDialog(this, "Calories must be positive.");
                    return;
                }
                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please describe the foods eaten.");
                    return;
                }

                calorieController.logEntry(navigationContext.getCurrentUser(),
                        new CalorieEntry(date, calories, description));
                JOptionPane.showMessageDialog(this, "Entry saved.");
                descriptionField.setText("");
                caloriesField.setText("");
                updateLog(logView);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> updateLog(logView));

        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel("Calories:"));
        add(caloriesField);
        add(new JLabel("Foods eaten / notes:"));
        add(new JScrollPane(descriptionField));
        add(save);
        add(refresh);
        add(new JScrollPane(logView));

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }

    private void updateLog(JTextArea logView) {
        if (!navigationContext.ensureLoggedIn()) {
            return;
        }
        String log = calorieController.list(navigationContext.getCurrentUser()).stream()
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .map(entry -> entry.getDate() + ": " + entry.getCalories() + " kcal - " + entry.getDescription())
                .collect(Collectors.joining("\n"));
        logView.setText(log.isEmpty() ? "No entries yet." : log);
    }
}
