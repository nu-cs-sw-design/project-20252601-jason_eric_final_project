import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryWorkoutRepository implements WorkoutRepository {

    private final Map<String, Map<LocalDate, Workout>> store = new HashMap<>();

    @Override
    public synchronized boolean add(String username, Workout workout) {
        if (username == null || workout == null) {
            return false;
        }
        Map<LocalDate, Workout> workouts =
                store.computeIfAbsent(username, u -> new HashMap<>());
        if (workouts.containsKey(workout.getDate())) {
            return false;
        }
        workouts.put(workout.getDate(), workout);
        return true;
    }

    @Override
    public synchronized boolean update(String username, Workout workout) {
        if (username == null || workout == null) {
            return false;
        }
        Map<LocalDate, Workout> workouts = store.get(username);
        if (workouts == null || !workouts.containsKey(workout.getDate())) {
            return false;
        }
        workouts.put(workout.getDate(), workout);
        return true;
    }

    @Override
    public synchronized Optional<Workout> find(String username, LocalDate date) {
        Map<LocalDate, Workout> workouts = store.get(username);
        if (workouts == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(workouts.get(date));
    }

    @Override
    public synchronized boolean delete(String username, LocalDate date) {
        Map<LocalDate, Workout> workouts = store.get(username);
        if (workouts == null) {
            return false;
        }
        boolean removed = workouts.remove(date) != null;
        if (workouts.isEmpty()) {
            store.remove(username);
        }
        return removed;
    }

    @Override
    public synchronized boolean exists(String username, LocalDate date) {
        Map<LocalDate, Workout> workouts = store.get(username);
        return workouts != null && workouts.containsKey(date);
    }

    @Override
    public synchronized List<Workout> search(String username, WorkoutSearchCriteria criteria) {
        return list(username).stream()
                .filter(workout -> criteria.getDate()
                        .map(date -> workout.getDate().equals(date)).orElse(true))
                .filter(workout -> criteria.getWorkoutName()
                        .map(name -> workout.getName().toLowerCase().contains(name))
                        .orElse(true))
                .filter(workout -> criteria.getExerciseName()
                        .map(exName -> workout.getExercises().stream()
                                .anyMatch(ex -> ex.getName().toLowerCase().contains(exName)))
                        .orElse(true))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Workout> list(String username) {
        Map<LocalDate, Workout> workouts = store.get(username);
        if (workouts == null) {
            return List.of();
        }
        return new ArrayList<>(workouts.values());
    }
}
