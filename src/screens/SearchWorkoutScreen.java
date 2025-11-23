import javax.swing.*;

public class SearchWorkoutScreen extends JPanel {

    public SearchWorkoutScreen(AppFrame app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField searchField = new JTextField();
        JTextArea results = new JTextArea(10, 30);
        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> {
            String query = searchField.getText();
            String res = WorkoutDatabase.search(query);

            results.setText(res.isEmpty() ? "No matches found." : res);
        });

        add(new JLabel("Search by date, workout name, or exercise:"));
        add(searchField);
        add(searchBtn);
        add(new JScrollPane(results));
    }
}
