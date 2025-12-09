import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;

public class EditWorkoutScreen extends JPanel {

    private final WorkoutController workoutController;
    private final NavigationContext navigationContext;
    private Workout loadedWorkout;

    public EditWorkoutScreen(NavigationContext navigationContext, WorkoutController workoutController) {
        this.navigationContext = navigationContext;
        this.workoutController = workoutController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField();
        JTextField workoutName = new JTextField();
        JTextArea exercises = new JTextArea(6, 25);
        exercises.setBorder(BorderFactory.createEtchedBorder());

        JButton load = new JButton("Load Workout");
        JButton save = new JButton("Save Changes");

        load.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            try {
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                workoutController.getWorkout(navigationContext.getCurrentUser(), date)
                        .ifPresentOrElse(workout -> {
                            loadedWorkout = workout;
                            workoutName.setText(workout.getName());
                            exercises.setText(workout.getExercises().stream()
                                    .map(ex -> String.join(", ",
                                            ex.getName(),
                                            String.valueOf(ex.getSets()),
                                            String.valueOf(ex.getReps()),
                                            String.valueOf(ex.getWeight())))
                                    .reduce((a, b) -> a + "\n" + b)
                                    .orElse(""));
                        }, () -> {
                            loadedWorkout = null;
                            exercises.setText("");
                            workoutName.setText("");
                            JOptionPane.showMessageDialog(this,
                                    "No workout found for that date.");
                        });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
            }
        });

        save.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            if (loadedWorkout == null) {
                JOptionPane.showMessageDialog(this, "Load a workout first.");
                return;
            }
            try {
                String name = workoutName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Workout name required.");
                    return;
                }
                Workout updated = new Workout(loadedWorkout.getDate(), name);
                boolean[] parseError = {false};
                Arrays.stream(exercises.getText().split("\\n"))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .forEach(line -> {
                            String[] parts = line.split(",");
                            if (parts.length == 4) {
                                try {
                                    updated.addExercise(new Exercise(
                                            parts[0].trim(),
                                            Integer.parseInt(parts[1].trim()),
                                            Integer.parseInt(parts[2].trim()),
                                            Double.parseDouble(parts[3].trim())
                                    ));
                                } catch (NumberFormatException ex1) {
                                    parseError[0] = true;
                                }
                            } else {
                                parseError[0] = true;
                            }
                        });
                if (updated.getExercises().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter at least one exercise line.");
                    return;
                }
                if (workoutController.editWorkout(navigationContext.getCurrentUser(), updated)) {
                    loadedWorkout = updated;
                    if (parseError[0]) {
                        JOptionPane.showMessageDialog(this,
                                "Workout saved, but some exercise rows were skipped.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Workout updated.");
                    }
                    navigationContext.showScreen(AppFrame.MAIN_MENU);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Unable to update workout. Load it again.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to update workout: " + ex.getMessage());
            }
        });

        add(new JLabel("Workout Date (YYYY-MM-DD):"));
        add(dateField);
        add(load);
        add(new JLabel("Workout Name:"));
        add(workoutName);
        add(new JLabel("Exercises (name,sets,reps,weight):"));
        add(new JScrollPane(exercises));
        add(save);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
