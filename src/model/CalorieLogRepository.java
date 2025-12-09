import java.time.LocalDate;
import java.util.List;

public interface CalorieLogRepository {
    void log(String username, CalorieEntry entry);
    List<CalorieEntry> list(String username);
    int totalForRange(String username, LocalDate from, LocalDate to);
}
