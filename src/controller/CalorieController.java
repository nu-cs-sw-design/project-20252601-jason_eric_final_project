import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalorieController {

    private final CalorieLogRepository repository;
    private final List<CalorieLogObserver> observers = new ArrayList<>();

    public CalorieController(CalorieLogRepository repository) {
        this.repository = repository;
    }

    public void addObserver(CalorieLogObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CalorieLogObserver observer) {
        observers.remove(observer);
    }

    public void logEntry(String username, CalorieEntry entry) {
        repository.log(username, entry);
        notifyObservers(username);
    }

    public List<CalorieEntry> list(String username) {
        return repository.list(username);
    }

    public int totalBetween(String username, LocalDate from, LocalDate to) {
        return repository.totalForRange(username, from, to);
    }

    private void notifyObservers(String username) {
        List<CalorieEntry> entries = repository.list(username);
        observers.forEach(observer -> observer.onCalorieLogChanged(username, entries));
    }
}
