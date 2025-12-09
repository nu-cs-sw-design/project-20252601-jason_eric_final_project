import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        WorkoutRepository workoutRepository = new InMemoryWorkoutRepository();
        BodyInfoRepository bodyInfoRepository = new InMemoryBodyInfoRepository();
        CalorieLogRepository calorieLogRepository = new InMemoryCalorieLogRepository();

        AuthController authController = new AuthController(userRepository);
        WorkoutController workoutController = new WorkoutController(workoutRepository);
        BodyInfoController bodyInfoController = new BodyInfoController(bodyInfoRepository);
        CalorieController calorieController = new CalorieController(calorieLogRepository);

        SwingUtilities.invokeLater(() ->
                new AppFrame(authController, workoutController, bodyInfoController, calorieController));
    }
}
