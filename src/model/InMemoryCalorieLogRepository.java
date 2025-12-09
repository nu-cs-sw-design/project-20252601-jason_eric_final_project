import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCalorieLogRepository implements CalorieLogRepository {

    private final Map<String, List<CalorieEntry>> store = new HashMap<>();

    @Override
    public synchronized void log(String username, CalorieEntry entry) {
        store.computeIfAbsent(username, u -> new ArrayList<>()).add(entry);
    }

    @Override
    public synchronized List<CalorieEntry> list(String username) {
        return new ArrayList<>(store.getOrDefault(username, List.of()));
    }

    @Override
    public synchronized int totalForRange(String username, LocalDate from, LocalDate to) {
        return list(username).stream()
                .filter(entry -> !entry.getDate().isBefore(from) && !entry.getDate().isAfter(to))
                .mapToInt(CalorieEntry::getCalories)
                .sum();
    }
}
