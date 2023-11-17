import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/17/2023 - 1:34 PM
 * Description: ...
 */
public class DeleteSlangWord extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton deleteButton;

    private List<SlangWord> slangWordList;

    public DeleteSlangWord(List<SlangWord> slangWordList) {
        setTitle("Delete Slang Word");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        this.slangWordList = slangWordList;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSlangWord();
            }
        });
        deleteButton.setEnabled(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(searchLabel, constraints);
        constraints.gridx = 1;
        panel.add(searchField, constraints);
        constraints.gridx = 2;
        panel.add(searchButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        panel.add(resultScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        panel.add(deleteButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim().toUpperCase();
        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (SlangWord slangWord : slangWordList) {
            if (slangWord.getWord().equalsIgnoreCase(searchTerm)) {
                result.append("Slang Word: ").append(slangWord.getWord()).append("\n");
                result.append("Definitions: ").append(String.join(", ", slangWord.getDefinition())).append("\n");

                deleteButton.setEnabled(true);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No matching word found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            deleteButton.setEnabled(false);
            resultArea.setText("");
        } else {
            resultArea.setText(result.toString());
        }
    }

    private void deleteSlangWord() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this slang word?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String searchTerm = searchField.getText().trim().toUpperCase();
            SlangWord selectedSlangWord = null;

            for (SlangWord slangWord : slangWordList) {
                if (slangWord.getWord().equalsIgnoreCase(searchTerm)) {
                    selectedSlangWord = slangWord;
                    break;
                }
            }

            if (selectedSlangWord != null) {
                slangWordList.remove(selectedSlangWord);


                saveSlangWordsToFile();

                JOptionPane.showMessageDialog(this, "Slang word deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                searchField.setText("");
                resultArea.setText("");
                deleteButton.setEnabled(false);
            }
        }
    }

    private void saveSlangWordsToFile() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("slang.txt"))) {
            for (SlangWord slangWord : slangWordList) {
                printWriter.print(slangWord.getWord() + "`");
                printWriter.println(String.join("|", slangWord.getDefinition()));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error occurred while saving slang words to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
