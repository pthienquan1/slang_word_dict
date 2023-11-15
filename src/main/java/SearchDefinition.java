import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/14/2023 - 8:22 PM
 * Description: ...
 */
public class SearchDefinition extends JFrame {
    private JTextArea resultTextArea;
    private List<String> searchHistory;

    public SearchDefinition(List<SlangWord> slangWordList, List<String> searchHistory) {
        super("Search Definition");
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

        // Add components to the panel
        panel.add(searchField);
        panel.add(searchButton);

        // Define action for the Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                searchDefinition(searchQuery, slangWordList);
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchDefinition(String searchQuery, List<SlangWord> slangWordList) {
        StringBuilder result = new StringBuilder();
        boolean foundMatch = false;

        for (SlangWord slangWord : slangWordList) {
            if ( slangWord.getWord().equals(searchQuery) ) {
                result.append("Slang word: ").append(slangWord.getWord()).append(":").append("\n");
                for (String definition : slangWord.getDefinition()) {
                    result.append("Definition you searched: ").append(definition).append("\n");
                }
                foundMatch = true;
                break;
            }

            for (String definition : slangWord.getDefinition()) {
                if ( definition.toLowerCase().contains(searchQuery.toLowerCase()) ) {
                    result.append("Slang word: ").append(slangWord.getWord()).append(":").append("\n");
                    result.append("Definition you searched: ").append(definition).append("\n");
                    foundMatch = true;
                    break;
                }
            }
        }


        if ( !foundMatch ) {
            result.append("No matching words found.");
        }

        resultTextArea.setText(result.toString());
        resultTextArea.setCaretPosition(0);
        searchHistory.add(searchQuery);

    }

    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }
}
