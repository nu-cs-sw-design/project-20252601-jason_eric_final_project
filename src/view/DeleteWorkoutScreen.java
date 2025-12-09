import javax.swing.*;
import java.time.LocalDate;

public class DeleteWorkoutScreen extends JPanel {

    private final WorkoutController workoutController;

    public DeleteWorkoutScreen(AppFrame app, WorkoutController workoutController) {
        this.workoutController = workoutController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField date = new JTextField();
        JButton delete = new JButton("Delete Workout");

        delete.addActionListener(e -> {
            if (!app.ensureLoggedIn()) {
                return;
            }
            try {
                LocalDate target = LocalDate.parse(date.getText().trim());
                if (workoutController.deleteWorkout(app.getCurrentUser(), target)) {
                    JOptionPane.showMessageDialog(this, "Workout deleted.");
                    app.showScreen(AppFrame.MAIN_MENU);
                } else {
                    JOptionPane.showMessageDialog(this, "No workout on this date.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
            }
        });

        add(new JLabel("Enter Date to Delete (YYYY-MM-DD):"));
        add(date);
        add(delete);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> app.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
