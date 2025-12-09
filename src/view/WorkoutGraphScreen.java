import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkoutGraphScreen extends JPanel {

    private final WorkoutController workoutController;

    public WorkoutGraphScreen(AppFrame app, WorkoutController workoutController) {
        this.workoutController = workoutController;

        setLayout(new BorderLayout());

        JTextArea summary = new JTextArea(10, 30);
        summary.setEditable(false);

        JButton refresh = new JButton("Refresh Summary");
        refresh.addActionListener(e -> {
            if (!app.ensureLoggedIn()) {
                return;
            }
            List<Workout> workouts = workoutController.list(app.getCurrentUser()).stream()
                    .sorted(Comparator.comparing(Workout::getDate))
                    .collect(Collectors.toList());
            if (workouts.isEmpty()) {
                summary.setText("No workouts recorded.");
                return;
            }

            StringBuilder builder = new StringBuilder();
            builder.append("Total workouts: ").append(workouts.size()).append("\n");
            builder.append("First: ").append(workouts.get(0).getDate())
                    .append(", Last: ").append(workouts.get(workouts.size() - 1).getDate())
                    .append("\n\n");

            Map<String, Integer> exerciseCounts = new HashMap<>();
            workouts.forEach(workout -> workout.getExercises()
                    .forEach(ex -> exerciseCounts.merge(ex.getName(), 1, Integer::sum)));

            builder.append("Exercise frequency:\n");
            exerciseCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(5)
                    .forEach(entry -> builder.append(" - ")
                            .append(entry.getKey())
                            .append(": ")
                            .append(entry.getValue())
                            .append(" sessions\n"));
            builder.append("\nDetailed log:\n");
            workouts.forEach(workout -> builder.append(workout.describe()).append("\n\n"));
            summary.setText(builder.toString());
        });

        add(new JScrollPane(summary), BorderLayout.CENTER);
        add(refresh, BorderLayout.SOUTH);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> app.showScreen(AppFrame.MAIN_MENU));
        add(back, BorderLayout.NORTH);
    }
}
