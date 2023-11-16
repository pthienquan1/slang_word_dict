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
    private List<JTextField> definitionTextFields;
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

        JLabel slangWordLabel = new JLabel("Slang Word:");
        slangWordTextField = new JTextField(20);
        slangWordTextField.setEditable(false);

        JLabel definitionLabel = new JLabel("Definition:");
        definitionTextFields = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Maximum number of definition text fields (adjust as needed)
            JTextField definitionTextField = new JTextField(20);
            definitionTextField.setEditable(false);
            definitionTextFields.add(definitionTextField);
        }

        JScrollPane definitionScrollPane = new JScrollPane(definitionTextFields.get(0));

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
        constraints.gridwidth = 3;
        panel.add(resultScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(slangWordLabel, constraints);
        constraints.gridx = 1;
        panel.add(slangWordTextField, constraints);

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

        for (SlangWord slangWord : slangWordList) {
            if ( slangWord.getWord().toUpperCase().equals(searchTerm) ) {
                slangWordTextField.setText(slangWord.getWord());

                List<String> definitions = slangWord.getDefinition();
                result.append("Definitions: ").append(String.join(", ", definitions)).append("\n");
                int numDefinitions = Math.min(definitions.size(), definitionTextFields.size());

                for (int i = 0; i < numDefinitions; i++) {
                    JTextField definitionTextField = definitionTextFields.get(i);
                    definitionTextField.setEditable(true);
                    definitionTextField.setText(definitions.get(i));
                }

                for (int i = numDefinitions; i < definitionTextFields.size(); i++) {
                    JTextField definitionTextField = definitionTextFields.get(i);
                    definitionTextField.setEditable(false);
                    definitionTextField.setText("");
                }

                selectedSlangWord = slangWord;
                editButton.setEnabled(true);
                break;
            }
        }

        if ( result.length() == 0 ) {
            result.append("No matching word found.");
            editButton.setEnabled(false);
        }

        resultArea.setText(result.toString());
    }

    private void editSlangWord() {
        List<String> newDefinitions = new ArrayList<>();
        for (JTextField definitionTextField : definitionTextFields) {
            String definition = definitionTextField.getText().trim();
            if ( !definition.isEmpty() ) {
                newDefinitions.add(definition);
            }
        }

        if ( selectedSlangWord != null && !newDefinitions.isEmpty() ) {
            selectedSlangWord.setDefinition(newDefinitions);

            try {
                FileWriter fileWriter = new FileWriter("slang.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (SlangWord slangWord : slangWordList) {
                    printWriter.println(slangWord.getWord() + "|" + String.join("|", slangWord.getDefinition()));
                }

                printWriter.close();
                fileWriter.close();

                JOptionPane.showMessageDialog(this, "Slang word edited successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while editing slang word.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            JScrollPane definitionScrollPane = new JScrollPane(definitionTextFields.get(0));
            searchField.setText("");
            resultArea.setText("");
            slangWordTextField.setText("");
            for (JTextField definitionTextField : definitionTextFields) {
                definitionTextField.setText("");
                definitionTextField.setEditable(false);
            }
            editButton.setEnabled(false);
        }
    }

}
