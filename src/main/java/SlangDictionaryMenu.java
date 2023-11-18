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
 * Description: ...
 */
public class SlangDictionaryMenu extends JFrame {
    protected static Map<String, SlangWord> slangWordMap;
    private List<String> searchHistorySlangWord;
    private static List<String> searchHistoryDefinition;
    private static Map<String, SlangWord> originalSlangWordMap;
    public SlangDictionaryMenu() {
        super("Slang Dictionary Menu");

        // Load slang words from the file
        slangWordMap = loadSlangWords("slang.txt");
        originalSlangWordMap = new HashMap<>(slangWordMap);

        searchHistorySlangWord = new ArrayList<>();
        searchHistoryDefinition = new ArrayList<>();
        // Create components
        JPanel panel = new JPanel();
        JButton searchSlangButton = new JButton("SearchSlang");
        JButton searchDefinitionButton = new JButton("Search Definition");
        JButton historyButton = new JButton("Search History");
        JButton addSlangButton = new JButton("Add Slang");
        JButton editButton = new JButton("Edit Slang word");
        JButton deleteButton = new JButton("Delete Slang word");
        JButton resetButton = new JButton("Reset");
        JButton randomButton = new JButton("Random Slang word");
        JButton quizz1Button  = new JButton("Quizz 1");
        JButton quizz2Button  = new JButton("Quizz 2");
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
        // Set layout manager for the frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(panel);

        // Define action for the SearchSlang button
        searchSlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchSlang searchSlang = new SearchSlang(new ArrayList<>(slangWordMap.values()));
                searchSlang.setSearchHistory(searchHistorySlangWord);
            }
        });
        searchDefinitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new SearchDefinition(slangWordMap, searchHistoryDefinition);


            }
        });


        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistorySearch(searchHistorySlangWord,searchHistoryDefinition);
            }
        });
        addSlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddSlangWord();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditSlangWord(new ArrayList<>(slangWordMap.values()));
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteSlangWord(slangWordMap);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSlangWordsToOriginal();
                JOptionPane.showMessageDialog(SlangDictionaryMenu.this, "Slang words reset to original state.", "Reset", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RandomSlangWord(slangWordMap);
            }
        });
        quizz1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Quizz1(slangWordMap);
            }
        });
        quizz2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Quizz2(slangWordMap);
            }
        });

        // Set frame properties
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static Map<String, SlangWord> loadSlangWords(String filename) {
        Map<String, SlangWord> slangWords = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("`");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String[] definitions = parts[1].split("\\|");
                    List<String> definitionList = new ArrayList<>();
                    for (String def : definitions) {
                        definitionList.add(def.trim());
                    }
                    slangWords.put(word, new SlangWord(word, definitionList));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return slangWords;
    }
    private void saveSlangWordsToFile() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("slang.txt"))) {
            for (SlangWord slangWord : slangWordMap.values()) {
                printWriter.print(slangWord.getWord() + "`");
                printWriter.println(String.join("|", slangWord.getDefinition()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void resetSlangWordsToOriginal() {
        slangWordMap.clear();
        slangWordMap.putAll(originalSlangWordMap);
        saveSlangWordsToFile();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SlangDictionaryMenu();
            }
        });
    }
}
