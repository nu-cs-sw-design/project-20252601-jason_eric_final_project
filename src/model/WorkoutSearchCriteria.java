import java.time.LocalDate;
import java.util.Optional;

public class WorkoutSearchCriteria {

    private final Optional<LocalDate> date;
    private final Optional<String> workoutName;
    private final Optional<String> exerciseName;

    public WorkoutSearchCriteria(Optional<LocalDate> date,
                                 Optional<String> workoutName,
                                 Optional<String> exerciseName) {
        this.date = date;
        this.workoutName = workoutName.map(String::toLowerCase);
        this.exerciseName = exerciseName.map(String::toLowerCase);
    }

    public Optional<LocalDate> getDate() {
        return date;
    }

    public Optional<String> getWorkoutName() {
        return workoutName;
    }

    public Optional<String> getExerciseName() {
        return exerciseName;
    }
}
