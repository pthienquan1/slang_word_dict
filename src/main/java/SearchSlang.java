import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/14/2023 - 11:18 AM
 * Description: Search Slang Word
 */
public class SearchSlang extends JFrame {
    private final JTextField searchField;
    private final JTextArea resultArea;
    private final List<SlangWord> slangWordList;
    private List<String> searchHistory;

    public SearchSlang(List<SlangWord> slangWordList) {
        super("Slang Dictionary");

        this.slangWordList = slangWordList;
        searchHistory = new ArrayList<>();
        // Create components
        JPanel panel = new JPanel();
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(15, 30);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 10));

        // Add components to the panel
        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Define action for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        StringBuilder result = new StringBuilder();

        for (SlangWord slangWord : slangWordList) {
            List<String> definitions = slangWord.getDefinition();
            if (slangWord.getWord().toUpperCase().contains(searchTerm.toUpperCase())) {
                result.append(slangWord.getWord()).append(": ");
                for (int i = 0; i < definitions.size(); i++) {
                    result.append(definitions.get(i));
                    if (i < definitions.size() - 1) {
                        result.append(", ");
                    }
                }
                result.append("\n");
            }
        }

        if (result.length() == 0) {
            result.append("No matching words found.");
        }

        resultArea.setText(result.toString());
        searchHistory.add(searchTerm);
        resultArea.setCaretPosition(0);
    }


    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }
}
