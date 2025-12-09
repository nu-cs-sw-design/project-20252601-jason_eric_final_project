import java.time.LocalDate;
import java.util.List;

public class CalorieController {

    private final CalorieLogRepository repository;

    public CalorieController(CalorieLogRepository repository) {
        this.repository = repository;
    }

    public void logEntry(String username, CalorieEntry entry) {
        repository.log(username, entry);
    }

    public List<CalorieEntry> list(String username) {
        return repository.list(username);
    }

    public int totalBetween(String username, LocalDate from, LocalDate to) {
        return repository.totalForRange(username, from, to);
    }
}
