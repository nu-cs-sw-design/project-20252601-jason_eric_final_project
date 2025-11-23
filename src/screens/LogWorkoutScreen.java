import javax.swing.*;

public class LogWorkoutScreen extends JPanel {

    public LogWorkoutScreen(AppFrame app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField date = new JTextField();
        JTextField workoutName = new JTextField();
        JTextArea exercises = new JTextArea(5,25);

        JButton save = new JButton("Save Workout");

        save.addActionListener(e -> {
            if (date.getText().isEmpty() || workoutName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Date and workout name are required.");
                return;
            }

            if (WorkoutDatabase.dateExists(date.getText())) {
                JOptionPane.showMessageDialog(this, "Workout already exists for this date.");
                return;
            }

            WorkoutDatabase.addWorkout(date.getText(), workoutName.getText(), exercises.getText());
            JOptionPane.showMessageDialog(this, "Workout logged!");
            app.showScreen(AppFrame.MAIN_MENU);
        });

        add(new JLabel("Date:"));
        add(date);
        add(new JLabel("Workout Name:"));
        add(workoutName);
        add(new JLabel("Exercises (name, sets, reps, weight):"));
        add(new JScrollPane(exercises));
        add(save);
    }
}
