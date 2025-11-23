import java.util.HashMap;

public class WorkoutDatabase {
    private static HashMap<String,String> workouts = new HashMap<>();

    public static boolean dateExists(String date) {
        return workouts.containsKey(date);
    }

    public static void addWorkout(String date, String name, String details) {
        workouts.put(date, name + "\n" + details);
    }

    public static void deleteWorkout(String date) {
        workouts.remove(date);
    }

    public static String search(String query) {
        StringBuilder sb = new StringBuilder();
        workouts.forEach((date,workout) -> {
            if (date.contains(query) || workout.contains(query))
                sb.append(date).append(":\n").append(workout).append("\n\n");
        });
        return sb.toString();
    }
}
