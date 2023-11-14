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
 * Date 11/14/2023 - 7:09 PM
 * Description: ...
 */
public class SlangDictionaryMenu extends JFrame {
    private List<SlangWord> slangWordList;

    public SlangDictionaryMenu() {
        super("Slang Dictionary Menu");

        // Load slang words from the file
        slangWordList = loadSlangWords("slang.txt");

        // Create components
        JPanel panel = new JPanel();
        JButton searchSlangButton = new JButton("SearchSlang");
        JButton searchDefinitionButton = new JButton("Search Definition");

        // Add components to the panel
        panel.add(searchSlangButton);
        panel.add(searchDefinitionButton);

        // Set layout manager for the frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(panel);

        // Define action for the SearchSlang button
        searchSlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchSlang(slangWordList);
            }
        });
        searchDefinitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchDefinition(slangWordList);
            }
        });


        // Set frame properties
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<SlangWord> loadSlangWords(String filename) {
        List<SlangWord> slangWords = new ArrayList<>();

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
                    slangWords.add(new SlangWord(word, definitionList));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return slangWords;
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
