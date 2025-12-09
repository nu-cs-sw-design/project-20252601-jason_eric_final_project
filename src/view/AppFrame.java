import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame implements NavigationContext {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final AuthController authController;
    private final WorkoutController workoutController;
    private final BodyInfoController bodyInfoController;
    private final CalorieController calorieController;

    private String currentUser;

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

    public AppFrame(AuthController authController,
                    WorkoutController workoutController,
                    BodyInfoController bodyInfoController,
                    CalorieController calorieController) {

        this.authController = authController;
        this.workoutController = workoutController;
        this.bodyInfoController = bodyInfoController;
        this.calorieController = calorieController;

        setTitle("Fitness App (MVC)");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        registerScreens();
        add(cardPanel);
        setVisible(true);
        showScreen(WELCOME);
    }

    private void registerScreens() {
        cardPanel.add(new WelcomeScreen(this), WELCOME);
        cardPanel.add(new LoginScreen(this, authController), LOGIN);
        cardPanel.add(new CreateAccountScreen(this, authController), CREATE_ACCOUNT);
        cardPanel.add(new MainMenuScreen(this), MAIN_MENU);
        cardPanel.add(new LogWorkoutScreen(this, workoutController), LOG_WORKOUT);
        cardPanel.add(new EditWorkoutScreen(this, workoutController), EDIT_WORKOUT);
        cardPanel.add(new DeleteWorkoutScreen(this, workoutController), DELETE_WORKOUT);
        cardPanel.add(new SearchWorkoutScreen(this, workoutController), SEARCH_WORKOUT);
        cardPanel.add(new BodyInfoScreen(this, bodyInfoController), BODY_INFO);
        cardPanel.add(new CalorieLogScreen(this, calorieController), CALORIE_LOG);
        cardPanel.add(new WorkoutGraphScreen(this, workoutController), WORKOUT_GRAPH);
        cardPanel.add(new CalorieGraphScreen(this, calorieController), CALORIE_GRAPH);
    }

    @Override
    public void showScreen(String name) {
        cardLayout.show(cardPanel, name);
    }

    @Override
    public String getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean ensureLoggedIn() {
        if (currentUser == null || currentUser.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Please log in first.", "Not logged in", JOptionPane.WARNING_MESSAGE);
            showScreen(LOGIN);
            return false;
        }
        return true;
    }
}
