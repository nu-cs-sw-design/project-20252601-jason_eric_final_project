import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;

public class LogWorkoutScreen extends JPanel {

    private final WorkoutController workoutController;

    public LogWorkoutScreen(AppFrame app, WorkoutController workoutController) {
        this.workoutController = workoutController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField("2025-01-01");
        JTextField workoutName = new JTextField();
        JTextArea exercises = new JTextArea(6, 25);
        exercises.setBorder(BorderFactory.createEtchedBorder());

        JButton save = new JButton("Save Workout");

        save.addActionListener(e -> {
            if (!app.ensureLoggedIn()) {
                return;
            }
            try {
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                String name = workoutName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Workout name required.");
                    return;
                }

                Workout workout = new Workout(date, name);
                boolean[] parseError = {false};
                Arrays.stream(exercises.getText().split("\\n"))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .forEach(line -> {
                            String[] parts = line.split(",");
                            if (parts.length == 4) {
                                try {
                                    workout.addExercise(new Exercise(
                                            parts[0].trim(),
                                            Integer.parseInt(parts[1].trim()),
                                            Integer.parseInt(parts[2].trim()),
                                            Double.parseDouble(parts[3].trim())
                                    ));
                                } catch (NumberFormatException ex) {
                                    parseError[0] = true;
                                }
                            } else {
                                parseError[0] = true;
                            }
                        });

                if (workout.getExercises().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please add at least one exercise (name,sets,reps,weight).");
                    return;
                }

                boolean saved = workoutController.logWorkout(app.getCurrentUser(), workout);
                if (!saved) {
                    JOptionPane.showMessageDialog(this,
                            "Workout already exists for that date. Use Edit instead.");
                    return;
                }

                if (parseError[0]) {
                    JOptionPane.showMessageDialog(this,
                            "Workout saved, but some exercise rows were skipped due to formatting.");
                } else {
                    JOptionPane.showMessageDialog(this, "Workout logged!");
                }
                app.showScreen(AppFrame.MAIN_MENU);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to save workout: " + ex.getMessage());
            }
        });

        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel("Workout Name:"));
        add(workoutName);
        add(new JLabel("Exercises (name,sets,reps,weight per line):"));
        add(new JScrollPane(exercises));
        add(save);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> app.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
