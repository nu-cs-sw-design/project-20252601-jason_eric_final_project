import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JPanel {

    public MainMenuScreen(AppFrame app) {
        setLayout(new GridLayout(10,1));

        add(makeButton("Log Workout", app, AppFrame.LOG_WORKOUT));
        add(makeButton("Edit Workout", app, AppFrame.EDIT_WORKOUT));
        add(makeButton("Delete Workout", app, AppFrame.DELETE_WORKOUT));
        add(makeButton("Search Workouts", app, AppFrame.SEARCH_WORKOUT));
        add(makeButton("Workout Graphs", app, AppFrame.WORKOUT_GRAPH));
        add(makeButton("Body Information", app, AppFrame.BODY_INFO));
        add(makeButton("Log Calories", app, AppFrame.CALORIE_LOG));
        add(makeButton("Calorie Graphs", app, AppFrame.CALORIE_GRAPH));
        add(makeButton("Logout", app, AppFrame.LOGIN));
    }

    private JButton makeButton(String label, AppFrame app, String target) {
        JButton b = new JButton(label);
        b.addActionListener(e -> app.showScreen(target));
        return b;
    }
}
