import javax.swing.*;
import java.time.LocalDate;

public class DeleteWorkoutScreen extends JPanel {

    private final WorkoutController workoutController;
    private final NavigationContext navigationContext;

    public DeleteWorkoutScreen(NavigationContext navigationContext, WorkoutController workoutController) {
        this.navigationContext = navigationContext;
        this.workoutController = workoutController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField date = new JTextField();
        JButton delete = new JButton("Delete Workout");

        delete.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            try {
                LocalDate target = LocalDate.parse(date.getText().trim());
                if (workoutController.deleteWorkout(navigationContext.getCurrentUser(), target)) {
                    JOptionPane.showMessageDialog(this, "Workout deleted.");
                    navigationContext.showScreen(AppFrame.MAIN_MENU);
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
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back);
    }
}
