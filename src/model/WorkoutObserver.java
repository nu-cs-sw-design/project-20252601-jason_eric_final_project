import java.util.List;

public interface WorkoutObserver {
    void onWorkoutChanged(String username, List<Workout> workouts);
}
