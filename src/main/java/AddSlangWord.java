import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/16/2023 - 11:25 AM
 * Description: ...
 */
public class AddSlangWord extends JFrame {
    private JTextField wordTextField;
    private List<JTextField> definitionTextFields;
    private JPanel definitionsPanel;
    public AddSlangWord() {
        super("Add Slang Word");

        // Create components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel wordLabel = new JLabel("Slang Word:");
        wordTextField = new JTextField(20);

        JLabel definitionLabel = new JLabel("Definition:");
        definitionTextFields = new ArrayList<>();
        definitionsPanel = new JPanel(new GridBagLayout());

        JButton addDefinitionButton = new JButton("Add 1 more definition");
        JButton addButton = new JButton("Add Slang");


        // Add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(wordLabel, constraints);
        constraints.gridx = 1;
        panel.add(wordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(definitionLabel, constraints);
        constraints.gridx = 1;
        panel.add(definitionsPanel, constraints);

        addDefinitionField();

        constraints.gridy = 2;
        panel.add(addDefinitionButton, constraints);

        constraints.gridy = 3;
        panel.add(addButton, constraints);

        // Define action for the Add Definition button
        addDefinitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDefinitionField();
                panel.revalidate();
                pack();
            }
        });

        // Define action for the Add Slang button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = wordTextField.getText().trim();
                List<String> definitions = new ArrayList<>();
                for (JTextField textField : definitionTextFields) {
                    String definition = textField.getText().trim();
                    if (!definition.isEmpty()) {
                        definitions.add(definition);
                    }
                }

                if (word.isEmpty() || definitions.isEmpty()) {
                    JOptionPane.showMessageDialog(AddSlangWord.this, "Please enter a slang word and at least one definition.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean wordExists = checkWordExists(word);
                if (wordExists) {
                    int choice = JOptionPane.showConfirmDialog(AddSlangWord.this,
                            "The slang word already exists. Do you want to overwrite it?",
                            "Duplicate Slang Word", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        overwriteSlangWord(word, definitions);
                        JOptionPane.showMessageDialog(AddSlangWord.this, "Slang word updated successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                } else {
                    addNewSlangWord(word, definitions);
                    JOptionPane.showMessageDialog(AddSlangWord.this, "Slang word added successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                }
            }
        });

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set frame properties
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void addDefinitionField() {
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        fieldConstraints.gridx = 0;
        fieldConstraints.gridy = definitionTextFields.size();

        JTextField definitionTextField = new JTextField(20);
        definitionTextFields.add(definitionTextField);
        definitionsPanel.add(definitionTextField, fieldConstraints);
    }
    private boolean checkWordExists(String word) {
        for (SlangWord slangWord : SlangDictionaryMenu.slangWordList) {
            if (slangWord.getWord().equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private void overwriteSlangWord(String word, List<String> definitions) {
        for (SlangWord slangWord : SlangDictionaryMenu.slangWordList) {
            if (slangWord.getWord().equalsIgnoreCase(word)) {
                slangWord.setDefinition(definitions);
                break;
            }
        }
        saveSlangWords("slang.txt");
    }

    private void addNewSlangWord(String word, List<String> definitions) {
        SlangDictionaryMenu.slangWordList.add(new SlangWord(word, definitions));
        saveSlangWords("slang.txt");
    }

    private void saveSlangWords(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (SlangWord slangWord : SlangDictionaryMenu.slangWordList) {
                writer.write(slangWord.getWord() + "`" + String.join("|", slangWord.getDefinition()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        wordTextField.setText("");
        for (JTextField textField : definitionTextFields) {
            textField.setText("");
        }
    }
}
