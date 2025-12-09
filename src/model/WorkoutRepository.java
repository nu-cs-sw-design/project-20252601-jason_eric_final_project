import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository {
    boolean add(String username, Workout workout);
    boolean update(String username, Workout workout);
    Optional<Workout> find(String username, LocalDate date);
    boolean delete(String username, LocalDate date);
    boolean exists(String username, LocalDate date);
    List<Workout> search(String username, WorkoutSearchCriteria criteria);
    List<Workout> list(String username);
}
