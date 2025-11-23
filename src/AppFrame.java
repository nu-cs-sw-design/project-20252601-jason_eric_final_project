import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    CardLayout cardLayout;
    JPanel cardPanel;

    // Screen names
    public static final String WELCOME = "WELCOME";
    public static final String LOGIN = "LOGIN";
    public static final String CREATE_ACCOUNT = "CREATE_ACCOUNT";
    public static final String MAIN_MENU = "MAIN_MENU";
    public static final String LOG_WORKOUT = "LOG_WORKOUT";
    public static final String EDIT_WORKOUT = "EDIT_WORKOUT";
    public static final String DELETE_WORKOUT = "DELETE_WORKOUT";
    public static final String SEARCH_WORKOUT = "SEARCH_WORKOUT";
    public static final String BODY_INFO = "BODY_INFO";
    public static final String CALORIE_LOG = "CALORIE_LOG";
    public static final String WORKOUT_GRAPH = "WORKOUT_GRAPH";
    public static final String CALORIE_GRAPH = "CALORIE_GRAPH";

    public AppFrame() {
        setTitle("Fitness App");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add screens
        cardPanel.add(new WelcomeScreen(this), WELCOME);
        cardPanel.add(new LoginScreen(this), LOGIN);
        cardPanel.add(new CreateAccountScreen(this), CREATE_ACCOUNT);
        cardPanel.add(new MainMenuScreen(this), MAIN_MENU);
        cardPanel.add(new LogWorkoutScreen(this), LOG_WORKOUT);
//        cardPanel.add(new EditWorkoutScreen(this), EDIT_WORKOUT);
        cardPanel.add(new DeleteWorkoutScreen(this), DELETE_WORKOUT);
        cardPanel.add(new SearchWorkoutScreen(this), SEARCH_WORKOUT);
        cardPanel.add(new BodyInfoScreen(this), BODY_INFO);
//        cardPanel.add(new CalorieLogScreen(this), CALORIE_LOG);
        cardPanel.add(new WorkoutGraphScreen(), WORKOUT_GRAPH);
        cardPanel.add(new CalorieGraphScreen(), CALORIE_GRAPH);

        add(cardPanel);
        setVisible(true);

        cardLayout.show(cardPanel, WELCOME);
    }

    public void showScreen(String name) {
        cardLayout.show(cardPanel, name);
    }
}
