import java.time.LocalDate;

public class CalorieEntry {

    private final LocalDate date;
    private final int calories;
    private final String description;

    public CalorieEntry(LocalDate date, int calories, String description) {
        this.date = date;
        this.calories = calories;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCalories() {
        return calories;
    }

    public String getDescription() {
        return description;
    }
}
