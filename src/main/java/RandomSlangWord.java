import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/18/2023 - 4:13 PM
 * Description: Random Slang Word
 */
public class RandomSlangWord extends JFrame {
    private final Map<String, SlangWord> slangWordMap;

    public RandomSlangWord(Map<String, SlangWord> slangWordMap) {
        super("Random Slang Word");
        this.slangWordMap = slangWordMap;

        // Create components
        JPanel panel = new JPanel();
        JButton randomButton = new JButton("Random Slang Word");
        JTextPane resultTextPane = new JTextPane();
        resultTextPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextPane);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add components to the panel
        panel.add(randomButton);

        // Define action for the Random button
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomSlangWord(resultTextPane);
            }
        });

        // Set frame properties
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void randomSlangWord(JTextPane resultTextPane) {
        if (slangWordMap.isEmpty()) {
            resultTextPane.setText("No slang words available.");
            return;
        }

        // Randomly select a slang word
        List<SlangWord> slangWordList = List.copyOf(slangWordMap.values());
        Random random = new Random();
        SlangWord randomSlangWord = slangWordList.get(random.nextInt(slangWordList.size()));

        // Display the result with formatted timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);

        String result = formattedDateTime + "\n\n";
        result += "- Random Slang Word: " + randomSlangWord.getWord() + "\n";
        result += "- Definitions: " + String.join(", ", randomSlangWord.getDefinition()) + "\n";

        resultTextPane.setText(result);
        resultTextPane.setParagraphAttributes(attributeSet, true);
        resultTextPane.setCaretPosition(0);
    }
}
