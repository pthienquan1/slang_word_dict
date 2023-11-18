import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;
import java.util.List;
/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/18/2023 - 10:02 PM
 * Description: ...
 */
public class Quizz2 extends JFrame {
    private Map<String, SlangWord> slangWordMap;
    private JTextField definitionTextField;
    private JButton[][] answerButtons;
    private String[] answerOptions;
    private String currentDefinition;
    private int correctAnswerPosition;

    public Quizz2(Map<String, SlangWord> slangWordMap) {
        super("Slang Definition Quiz");
        this.slangWordMap = slangWordMap;
        this.answerButtons = new JButton[2][2];
        this.answerOptions = new String[4];

        // Create components
        JPanel panel = new JPanel(new BorderLayout());
        definitionTextField = new JTextField();
        JPanel answersPanel = new JPanel(new GridLayout(2, 2));
        answersPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                answerButtons[i][j] = new JButton();
                answerButtons[i][j].setPreferredSize(new Dimension(30, 20));
                answersPanel.add(answerButtons[i][j]);
            }
        }

        // Add components to the panel
        panel.add(definitionTextField, BorderLayout.NORTH);
        panel.add(answersPanel, BorderLayout.CENTER);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Start the quiz
        startQuiz();
    }

    private void startQuiz() {
        if (slangWordMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No slang words available.", "Quiz Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Randomly select a definition
        List<SlangWord> slangWordList = List.copyOf(slangWordMap.values());
        Random random = new Random();
        SlangWord randomSlangWord = slangWordList.get(random.nextInt(slangWordList.size()));
        currentDefinition = randomSlangWord.getDefinition().get(0);

        // Display the definition
        definitionTextField.setText("What does '" + currentDefinition + "' mean?");
        definitionTextField.setFont(new Font("SansSerif", Font.BOLD, 16)); // Set font size for the definition

        // Randomly select a correct answer position
        correctAnswerPosition = random.nextInt(4);

        // Set the correct answer
        answerOptions[correctAnswerPosition] = randomSlangWord.getWord();

        // Fill other options with random slang words
        int slangWordIndex = 1;
        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerPosition) {
                String randomSlangWordText;
                do {
                    randomSlangWordText = getRandomSlangWordText(slangWordList);
                } while (randomSlangWordText.equals(answerOptions[0])); // Ensure options are unique
                answerOptions[i] = randomSlangWordText;
            }
        }

        // Set the button text
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                answerButtons[i][j].setText(answerOptions[i * 2 + j]);
                int finalI = i, finalJ = j;
                answerButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleAnswer(finalI * 2 + finalJ);
                    }
                });
            }
        }
    }

    private String getRandomSlangWordText(List<SlangWord> slangWordList) {
        Random random = new Random();
        SlangWord randomSlangWord = slangWordList.get(random.nextInt(slangWordList.size()));
        return randomSlangWord.getWord();
    }

    private void handleAnswer(int selectedAnswer) {
        String resultMessage;
        if (selectedAnswer == correctAnswerPosition) {
            resultMessage = "Correct! '" + currentDefinition + "' means '" + answerOptions[correctAnswerPosition] + "'.";
        } else {
            resultMessage = "Incorrect! Try again.";
            int option = JOptionPane.showConfirmDialog(this, resultMessage, "Quiz Result", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, resultMessage, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        // Reset the quiz
        dispose();
        new Quizz2(slangWordMap);
    }
}
