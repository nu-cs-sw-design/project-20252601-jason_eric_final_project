import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JPanel {

    public MainMenuScreen(AppFrame app) {
        setLayout(new GridLayout(0, 1, 5, 5));

        add(makeProtectedButton("Log Workout", app, AppFrame.LOG_WORKOUT));
        add(makeProtectedButton("Edit Workout", app, AppFrame.EDIT_WORKOUT));
        add(makeProtectedButton("Delete Workout", app, AppFrame.DELETE_WORKOUT));
        add(makeProtectedButton("Search Workouts", app, AppFrame.SEARCH_WORKOUT));
        add(makeProtectedButton("Workout Summary", app, AppFrame.WORKOUT_GRAPH));
        add(makeProtectedButton("Body Information", app, AppFrame.BODY_INFO));
        add(makeProtectedButton("Log Calories", app, AppFrame.CALORIE_LOG));
        add(makeProtectedButton("Calorie Summary", app, AppFrame.CALORIE_GRAPH));

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            app.setCurrentUser(null);
            app.showScreen(AppFrame.LOGIN);
        });
        add(logout);
    }

    private JButton makeProtectedButton(String label, AppFrame app, String target) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            if (app.ensureLoggedIn()) {
                app.showScreen(target);
            }
        });
        return button;
    }
}
