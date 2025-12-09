import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkoutGraphScreen extends JPanel implements WorkoutObserver {

    private final WorkoutController workoutController;
    private final NavigationContext navigationContext;
    private final JTextArea summary;

    public WorkoutGraphScreen(NavigationContext navigationContext, WorkoutController workoutController) {
        this.navigationContext = navigationContext;
        this.workoutController = workoutController;
        this.summary = new JTextArea(10, 30);

        setLayout(new BorderLayout());

        summary.setEditable(false);

        JButton refresh = new JButton("Refresh Summary");
        refresh.addActionListener(e -> refreshForCurrentUser());

        add(new JScrollPane(summary), BorderLayout.CENTER);
        add(refresh, BorderLayout.SOUTH);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back, BorderLayout.NORTH);

        workoutController.addObserver(this);
        refreshForCurrentUser();
    }

    private void refreshForCurrentUser() {
        String user = navigationContext.getCurrentUser();
        if (user == null || user.isBlank()) {
            summary.setText("Please log in to view workout summaries.");
            return;
        }
        List<Workout> workouts = workoutController.list(user).stream()
                .sorted(Comparator.comparing(Workout::getDate))
                .collect(Collectors.toList());
        summary.setText(format(workouts));
    }

    private String format(List<Workout> workouts) {
        if (workouts.isEmpty()) {
            return "No workouts recorded.";
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
        return builder.toString();
    }

    @Override
    public void onWorkoutChanged(String username, List<Workout> workouts) {
        if (navigationContext.getCurrentUser() == null ||
                !navigationContext.getCurrentUser().equals(username)) {
            return;
        }
        List<Workout> sorted = workouts.stream()
                .sorted(Comparator.comparing(Workout::getDate))
                .collect(Collectors.toList());
        summary.setText(format(sorted));
    }
}
