import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public boolean logWorkout(String username, Workout workout) {
        return workoutRepository.add(username, workout);
    }

    public boolean editWorkout(String username, Workout workout) {
        return workoutRepository.update(username, workout);
    }

    public Optional<Workout> getWorkout(String username, LocalDate date) {
        return workoutRepository.find(username, date);
    }

    public boolean deleteWorkout(String username, LocalDate date) {
        return workoutRepository.delete(username, date);
    }

    public boolean exists(String username, LocalDate date) {
        return workoutRepository.exists(username, date);
    }

    public List<Workout> search(String username, WorkoutSearchCriteria criteria) {
        return workoutRepository.search(username, criteria);
    }

    public List<Workout> list(String username) {
        return workoutRepository.list(username);
    }
}
