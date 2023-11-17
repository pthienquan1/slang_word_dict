import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/14/2023 - 8:22 PM
 * Description: ...
 */
public class SearchDefinition extends JFrame {
    private JTextArea resultTextArea;
    private JComboBox<String> searchHistoryComboBox;
    private Map<String, SlangWord> slangWordMap;
    private List<String> searchHistory;

    public SearchDefinition(Map<String, SlangWord> slangWordMap, List<String> searchHistory) {
        super("Search Definition");
        this.slangWordMap = slangWordMap;
        this.searchHistory = searchHistory;

        // Create components
        JPanel panel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Create a JComboBox for search history
        searchHistoryComboBox = new JComboBox<>(searchHistory.toArray(new String[0]));
        panel.add(searchHistoryComboBox);

        // Add components to the panel
        panel.add(searchField);
        panel.add(searchButton);

        // Define action for the Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                searchDefinition(searchQuery);
            }
        });

        // Define action for the JComboBox selection
        searchHistoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuery = (String) searchHistoryComboBox.getSelectedItem();
                if (selectedQuery != null) {
                    searchField.setText(selectedQuery);
                    searchDefinition(selectedQuery);
                }
            }
        });

        // Set frame properties
        pack();
        setSize(380, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchDefinition(String searchQuery) {
        StringBuilder result = new StringBuilder();
        boolean foundMatch = false;

        for (SlangWord slangWord : slangWordMap.values()) {
            if ( slangWord.getDefinition().contains(searchQuery) ) {
                result.append("Slang word: ").append(slangWord.getWord()).append(":").append("\n");
                result.append("Definition you searched: ").append(searchQuery).append("\n");
                foundMatch = true;
                break;
            }
        }

        if ( !foundMatch ) {
            result.append("No matching words found.");
            // If no matching slang word is found, clear the JComboBox
            searchHistoryComboBox.setSelectedIndex(-1);
        }

        resultTextArea.setText(result.toString());
        resultTextArea.setCaretPosition(0);

        // Add the search query to the search history
        if ( !searchHistory.contains(searchQuery) ) {
            searchHistory.add(searchQuery);
            // Update the JComboBox
            searchHistoryComboBox.removeAllItems();
            for (String historyItem : searchHistory) {
                searchHistoryComboBox.addItem(historyItem);
            }
            searchHistoryComboBox.setSelectedItem(searchQuery);
        }
    }
}
