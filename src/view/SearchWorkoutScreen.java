import javax.swing.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchWorkoutScreen extends JPanel {

    private final WorkoutController workoutController;
    private final NavigationContext navigationContext;

    public SearchWorkoutScreen(NavigationContext navigationContext, WorkoutController workoutController) {
        this.navigationContext = navigationContext;
        this.workoutController = workoutController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField();
        JTextField workoutNameField = new JTextField();
        JTextField exerciseField = new JTextField();
        JTextArea results = new JTextArea(12, 30);
        results.setEditable(false);
        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            Optional<LocalDate> date = parseDate(dateField.getText());
            if (dateField.getText().length() > 0 && date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
                return;
            }
            Optional<String> workoutName = optionalText(workoutNameField.getText());
            Optional<String> exerciseName = optionalText(exerciseField.getText());

            WorkoutSearchCriteria criteria =
                    new WorkoutSearchCriteria(date, workoutName, exerciseName);
            String res = workoutController.search(navigationContext.getCurrentUser(), criteria).stream()
                    .map(Workout::describe)
                    .collect(Collectors.joining("\n\n"));
            results.setText(res.isEmpty() ? "No matches found." : res);
        });

        add(new JLabel("Date (YYYY-MM-DD, optional):"));
        add(dateField);
        add(new JLabel("Workout name contains:"));
        add(workoutNameField);
        add(new JLabel("Exercise name contains:"));
        add(exerciseField);
        add(searchBtn);
        add(new JScrollPane(results));

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }

    private Optional<LocalDate> parseDate(String text) {
        text = text == null ? "" : text.trim();
        if (text.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.parse(text));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<String> optionalText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(text.trim());
    }
}
