import javax.swing.*;

public class DeleteWorkoutScreen extends JPanel {

    public DeleteWorkoutScreen(AppFrame app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField date = new JTextField();
        JButton delete = new JButton("Delete Workout");

        delete.addActionListener(e -> {
            if (!WorkoutDatabase.dateExists(date.getText())) {
                JOptionPane.showMessageDialog(this, "No workout on this date.");
                return;
            }
            WorkoutDatabase.deleteWorkout(date.getText());
            JOptionPane.showMessageDialog(this, "Workout deleted.");
            app.showScreen(AppFrame.MAIN_MENU);
        });

        add(new JLabel("Enter Date to Delete:"));
        add(date);
        add(delete);
    }
}
