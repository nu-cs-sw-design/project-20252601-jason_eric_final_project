import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Workout {

    private final LocalDate date;
    private final String name;
    private final List<Exercise> exercises = new ArrayList<>();

    public Workout(LocalDate date, String name) {
        this.date = date;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void clearExercises() {
        exercises.clear();
    }

    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append(date).append(" - ").append(name);

        if (!exercises.isEmpty()) {
            builder.append("\n");
            exercises.forEach(ex -> builder.append(" * ").append(ex).append("\n"));
        }

        return builder.toString().trim();
    }
}
