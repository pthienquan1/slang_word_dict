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
 * Description: ...
 */
public class SearchSlang extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;

    private List<SlangWord> slangWordList;

    public SearchSlang(List<SlangWord> slangWordList) {
        super("Slang Dictionary");

        this.slangWordList = slangWordList;

        // Create components
        JPanel panel = new JPanel();
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(15, 30);
        resultArea.setEditable(false);

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
        String searchTerm = searchField.getText().trim().toUpperCase();
        StringBuilder result = new StringBuilder();

        for (SlangWord slangWord : slangWordList) {
            if (slangWord.getWord().toUpperCase().contains(searchTerm)) {
                result.append(slangWord.getWord()).append(": ").append(SlangWord.asString(slangWord.getDefinition(), ", ")).append("\n");
            }
        }

        if (result.length() ==0) {
            result.append("No matching words found.");
        }

        resultArea.setText(result.toString());
    }
}
