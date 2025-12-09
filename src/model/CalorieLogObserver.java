import java.util.List;

public interface CalorieLogObserver {
    void onCalorieLogChanged(String username, List<CalorieEntry> entries);
}
