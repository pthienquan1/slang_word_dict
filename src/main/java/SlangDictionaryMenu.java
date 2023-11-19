import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/14/2023 - 7:09 PM
 * Description: Slang Dictionary Menu
 */
public class SlangDictionaryMenu extends JFrame {
    protected static Map<String, SlangWord> slangWordMap;
    private final List<String> searchHistorySlangWord;
    private static List<String> searchHistoryDefinition;
    protected Map<String, SlangWord> originalSlangWordMap;

    public SlangDictionaryMenu() {
        super("Slang Dictionary Menu");

        // Load slang words from the file
        slangWordMap = FileHandler.loadSlangWordsFromFile();
        initializeOriginalSlangWordMap();

        searchHistorySlangWord = new ArrayList<>();
        searchHistoryDefinition = new ArrayList<>();

        // Create components
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JButton searchSlangButton = new JButton("Search Slang");
        JButton searchDefinitionButton = new JButton("Search Definition");
        JButton historyButton = new JButton("Search History");
        JButton addSlangButton = new JButton("Add Slang");
        JButton editButton = new JButton("Edit Slang word");
        JButton deleteButton = new JButton("Delete Slang word");
        JButton resetButton = new JButton("Reset");
        JButton randomButton = new JButton("Random Slang word");
        JButton quizz1Button = new JButton("Quizz 1");
        JButton quizz2Button = new JButton("Quizz 2");

        // Add components to the panel
        panel.add(searchSlangButton);
        panel.add(searchDefinitionButton);
        panel.add(historyButton);
        panel.add(addSlangButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(resetButton);
        panel.add(randomButton);
        panel.add(quizz1Button);
        panel.add(quizz2Button);

        // Define action for the SearchSlang button
        searchSlangButton.addActionListener(e -> {
            SearchSlang searchSlang = new SearchSlang(new ArrayList<>(slangWordMap.values()));
            searchSlang.setSearchHistory(searchHistorySlangWord);
        });

        searchDefinitionButton.addActionListener(e -> new SearchDefinition(slangWordMap, searchHistoryDefinition));

        historyButton.addActionListener(e -> new HistorySearch(searchHistorySlangWord, searchHistoryDefinition));

        addSlangButton.addActionListener(e -> new AddSlangWord());

        editButton.addActionListener(e -> new EditSlangWord(new ArrayList<>(slangWordMap.values()), new HashMap<>(slangWordMap)));


        deleteButton.addActionListener(e -> new DeleteSlangWord(slangWordMap));

        resetButton.addActionListener(e -> {
            resetSlangWordsToOriginal();
            JOptionPane.showMessageDialog(SlangDictionaryMenu.this, "Slang words reset to original state.", "Reset", JOptionPane.INFORMATION_MESSAGE);
        });

        randomButton.addActionListener(e -> new RandomSlangWord(slangWordMap));

        quizz1Button.addActionListener(e -> new Quizz1(slangWordMap));

        quizz2Button.addActionListener(e -> new Quizz2(slangWordMap));

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initializeOriginalSlangWordMap() {
        originalSlangWordMap = new HashMap<>(slangWordMap);
    }

    private void resetSlangWordsToOriginal() {
        FileHandler.loadSlangWordsFromFile();
        slangWordMap.clear();
        slangWordMap.putAll(originalSlangWordMap);
        FileHandler.saveSlangWordsToFile(slangWordMap);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlangDictionaryMenu::new);
    }
}
