import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/18/2023 - 8:50 PM
 * Description: Quizz 1: 1 random slang word and 4 random definitions
 */
public class Quizz1 extends JFrame {
    private final Map<String, SlangWord> slangWordMap;
    private final JTextField questionTextField;
    private final JButton[][] answerButtons;
    private final String[] answerOptions;
    private SlangWord currentSlangWord;
    private int correctAnswerPosition;

    public Quizz1(Map<String, SlangWord> slangWordMap) {
        super("Slang Word Quiz");
        this.slangWordMap = slangWordMap;
        this.answerButtons = new JButton[2][2];
        this.answerOptions = new String[4];

        // Create components
        JPanel panel = new JPanel(new BorderLayout());
        questionTextField = new JTextField();
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
        panel.add(questionTextField, BorderLayout.NORTH);
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

        // Randomly select a slang word
        List<SlangWord> slangWordList = List.copyOf(slangWordMap.values());
        Random random = new Random();
        currentSlangWord = slangWordList.get(random.nextInt(slangWordList.size()));

        // Display the question
        questionTextField.setText("What is the definition of '" + currentSlangWord.getWord() + "'?");
        questionTextField.setFont(new Font("SansSerif", Font.BOLD, 16)); // Đặt kích thước font cho câu hỏi

        // Randomly select a correct answer position
        correctAnswerPosition = random.nextInt(4);

        // Set the correct answer
        answerOptions[correctAnswerPosition] = currentSlangWord.getDefinition().get(0);

        // Fill other options with random definitions
        int definitionIndex = 1;
        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerPosition) {
                String randomDefinition;
                do {
                    randomDefinition = getRandomDefinition(slangWordList);
                } while (randomDefinition.equals(answerOptions[0])); // Ensure options are unique
                answerOptions[i] = randomDefinition;
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

    private String getRandomDefinition(List<SlangWord> slangWordList) {
        Random random = new Random();
        SlangWord randomSlangWord = slangWordList.get(random.nextInt(slangWordList.size()));
        return randomSlangWord.getDefinition().get(0);
    }

    private void handleAnswer(int selectedAnswer) {
        String resultMessage;
        if (selectedAnswer == correctAnswerPosition) {
            resultMessage = "Correct! '" + currentSlangWord.getWord() + "' means '" + currentSlangWord.getDefinition().get(0) + "'.";
            JOptionPane.showMessageDialog(this, resultMessage, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);

            // Set the background color of the correct button to green
            int row = correctAnswerPosition / 2;
            int col = correctAnswerPosition % 2;
            answerButtons[row][col].setBackground(Color.GREEN);

            // Delay to show the green background before resetting
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Reset the background color of all buttons to default
                    resetQuiz();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            resultMessage = "Incorrect! Try again.";
            JOptionPane.showMessageDialog(this, resultMessage, "Quiz Result", JOptionPane.ERROR_MESSAGE);

            // Set the background color of the incorrect button to red
            int row = selectedAnswer / 2;
            int col = selectedAnswer % 2;
            answerButtons[row][col].setBackground(Color.RED);
        }
    }

    private void resetQuiz() {
        // Reset the background color of all buttons to default
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                answerButtons[i][j].setBackground(null);
            }
        }

        // Reset the quiz
        dispose();
        new Quizz1(slangWordMap);
    }
}
