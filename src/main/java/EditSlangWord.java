import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/16/2023 - 8:10 PM
 * Description: ...
 */
public class EditSlangWord extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JTextField slangWordTextField;
    private JTextField newSlangWordTextField;
    private JTextArea definitionTextArea;
    private JButton editButton;

    private List<SlangWord> slangWordList;
    private SlangWord selectedSlangWord;

    public EditSlangWord(List<SlangWord> slangWordList) {
        setTitle("Edit Slang Word");
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

        JLabel slangWordLabel = new JLabel("Current Slang Word:");
        slangWordTextField = new JTextField(20);
        slangWordTextField.setEditable(false);

        JLabel newSlangWordLabel = new JLabel("New Slang Word:");
        newSlangWordTextField = new JTextField(20);

        JLabel definitionLabel = new JLabel("Definitions:");
        definitionTextArea = new JTextArea(5, 30);
        JScrollPane definitionScrollPane = new JScrollPane(definitionTextArea);

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSlangWord();
            }
        });
        editButton.setEnabled(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(searchLabel, constraints);
        constraints.gridx = 1;
        panel.add(searchField, constraints);
        constraints.gridx = 2;
        panel.add(searchButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(slangWordLabel, constraints);
        constraints.gridx = 1;
        panel.add(slangWordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(newSlangWordLabel, constraints);
        constraints.gridx = 1;
        panel.add(newSlangWordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(definitionLabel, constraints);
        constraints.gridx = 1;
        panel.add(definitionScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(editButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim().toUpperCase();
        StringBuilder result = new StringBuilder();
        boolean found = false;

        for (SlangWord slangWord : SlangDictionaryMenu.slangWordMap.values()) {
            if (slangWord.getWord().equalsIgnoreCase(searchTerm)) {
                slangWordTextField.setText(slangWord.getWord());
                newSlangWordTextField.setText(slangWord.getWord());

                List<String> definitions = slangWord.getDefinition();
                result.append("Definitions: ").append(String.join(", ", definitions)).append("\n");
                definitionTextArea.setText(String.join("\n", addDashes(definitions)));

                selectedSlangWord = slangWord;
                editButton.setEnabled(true);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No matching word found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            editButton.setEnabled(false);
            resultArea.setText("");
            slangWordTextField.setText("");
            newSlangWordTextField.setText("");
            definitionTextArea.setText("");
        } else {
            resultArea.setText(result.toString());
        }
    }

    private List<String> addDashes(List<String> definitions) {
        List<String> definitionsWithDashes = new ArrayList<>();
        for (String definition : definitions) {
            definitionsWithDashes.add("- " + definition);
        }
        return definitionsWithDashes;
    }

    private void editSlangWord() {
        String newSlangWord = newSlangWordTextField.getText().trim();
        List<String> newDefinitions = new ArrayList<>();
        String[] definitionLines = definitionTextArea.getText().split("\n");

        for (String definition : definitionLines) {
            String trimmedDefinition = definition.trim();
            if (!trimmedDefinition.isEmpty()) {
                newDefinitions.add(trimmedDefinition.substring(2));
            }
        }

        if (!newSlangWord.isEmpty() && !newDefinitions.isEmpty()) {
            selectedSlangWord.setWord(newSlangWord);
            selectedSlangWord.setDefinition(newDefinitions);

            try (PrintWriter printWriter = new PrintWriter(new FileWriter("slang.txt"))) {
                for (SlangWord slangWord : SlangDictionaryMenu.slangWordMap.values()) {
                    printWriter.print(slangWord.getWord() + "`");
                    printWriter.println(String.join("|", slangWord.getDefinition()));
                }

                JOptionPane.showMessageDialog(this, "Slang word edited successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while editing slang word.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            searchField.setText("");
            resultArea.setText("");
            slangWordTextField.setText("");
            newSlangWordTextField.setText("");
            definitionTextArea.setText("");
            editButton.setEnabled(false);
        }
    }


}
