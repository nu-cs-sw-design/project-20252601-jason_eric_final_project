import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JPanel {

    private final NavigationContext navigationContext;

    public MainMenuScreen(NavigationContext navigationContext) {
        this.navigationContext = navigationContext;
        setLayout(new GridLayout(0, 1, 5, 5));

        add(makeProtectedButton("Log Workout", AppFrame.LOG_WORKOUT));
        add(makeProtectedButton("Edit Workout", AppFrame.EDIT_WORKOUT));
        add(makeProtectedButton("Delete Workout", AppFrame.DELETE_WORKOUT));
        add(makeProtectedButton("Search Workouts", AppFrame.SEARCH_WORKOUT));
        add(makeProtectedButton("Workout Summary", AppFrame.WORKOUT_GRAPH));
        add(makeProtectedButton("Body Information", AppFrame.BODY_INFO));
        add(makeProtectedButton("Log Calories", AppFrame.CALORIE_LOG));
        add(makeProtectedButton("Calorie Summary", AppFrame.CALORIE_GRAPH));

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            navigationContext.setCurrentUser(null);
            navigationContext.showScreen(AppFrame.LOGIN);
        });
        add(logout);
    }

    private JButton makeProtectedButton(String label, String target) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            if (navigationContext.ensureLoggedIn()) {
                navigationContext.showScreen(target);
            }
        });
        return button;
    }
}
