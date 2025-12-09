import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalorieGraphScreen extends JPanel {

    private final CalorieController calorieController;
    private final NavigationContext navigationContext;

    public CalorieGraphScreen(NavigationContext navigationContext, CalorieController calorieController) {
        this.navigationContext = navigationContext;
        this.calorieController = calorieController;

        setLayout(new BorderLayout());

        JTextArea summary = new JTextArea("No data");
        summary.setEditable(false);
        summary.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(summary), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh 7-day trend");
        refresh.addActionListener(e -> {
            if (!navigationContext.ensureLoggedIn()) {
                return;
            }
            LocalDate today = LocalDate.now();
            LocalDate start = today.minusDays(6);
            int total = calorieController.totalBetween(navigationContext.getCurrentUser(), start, today);

            Map<LocalDate, Integer> perDay = new HashMap<>();
            List<CalorieEntry> entries = calorieController.list(navigationContext.getCurrentUser());
            entries.stream()
                    .filter(entry -> !entry.getDate().isBefore(start) && !entry.getDate().isAfter(today))
                    .forEach(entry -> perDay.merge(entry.getDate(), entry.getCalories(), Integer::sum));

            StringBuilder builder = new StringBuilder();
            builder.append("Last 7 days total: ").append(total).append(" kcal\n\n");
            for (int i = 6; i >= 0; i--) {
                LocalDate day = today.minusDays(i);
                int calories = perDay.getOrDefault(day, 0);
                int bars = Math.min(calories / 50, 50);
                builder.append(day)
                        .append(" | ")
                        .append("#".repeat(Math.max(bars, 0)))
                        .append(" (").append(calories).append(" kcal)\n");
            }
            summary.setText(builder.toString());
        });

        add(refresh, BorderLayout.SOUTH);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> navigationContext.showScreen(AppFrame.MAIN_MENU));
        add(back, BorderLayout.NORTH);
    }
}
