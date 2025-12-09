import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final List<WorkoutObserver> observers = new ArrayList<>();

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void addObserver(WorkoutObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WorkoutObserver observer) {
        observers.remove(observer);
    }

    public boolean logWorkout(String username, Workout workout) {
        boolean saved = workoutRepository.add(username, workout);
        if (saved) {
            notifyObservers(username);
        }
        return saved;
    }

    public boolean editWorkout(String username, Workout workout) {
        boolean updated = workoutRepository.update(username, workout);
        if (updated) {
            notifyObservers(username);
        }
        return updated;
    }

    public Optional<Workout> getWorkout(String username, LocalDate date) {
        return workoutRepository.find(username, date);
    }

    public boolean deleteWorkout(String username, LocalDate date) {
        boolean deleted = workoutRepository.delete(username, date);
        if (deleted) {
            notifyObservers(username);
        }
        return deleted;
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

    private void notifyObservers(String username) {
        List<Workout> snapshot = workoutRepository.list(username);
        observers.forEach(observer -> observer.onWorkoutChanged(username, snapshot));
    }
}
