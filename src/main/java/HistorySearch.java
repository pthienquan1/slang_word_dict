import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/15/2023 - 2:20 PM
 * Description: History Search
 */
public class HistorySearch extends JFrame {
    private List<String> historyList;

    public HistorySearch(List<String> searchHistorySlang, List<String> searchHistoryDefinition) {
        super("Search History");

        this.historyList = new ArrayList<>();
        this.historyList.addAll(searchHistorySlang);
        this.historyList.addAll(searchHistoryDefinition);

        // Create components
        JPanel panel = new JPanel();
        JTextArea historyTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);

        // Set text for the history text area
        StringBuilder sb = new StringBuilder();
        for (String history : historyList) {
            if (searchHistorySlang.contains(history)) {
                sb.append(" - Slang word searched: ");
            } else if (searchHistoryDefinition.contains(history)) {
                sb.append(" + Definition searched: ");
            }
            sb.append(history).append("\n");
        }
        historyTextArea.setText(sb.toString());
        historyTextArea.setEditable(false);

        // Add components to the panel
        panel.add(scrollPane);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
