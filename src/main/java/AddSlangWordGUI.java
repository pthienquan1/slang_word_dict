import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 1:49 PM
 * Description: ...
 */
public class AddSlangWordGUI extends PanelForChange{
    private final JPanel definitionsPanel;
    protected final ArrayList<TextLabelWithDestroyButton> definitionFields = new ArrayList<>();


    public AddSlangWordGUI(String title, Function<String, String> onGetDefinition, BiPredicate<SlangWord, BooleanSupplier> onAddSlang)
    {
        super(title, onGetDefinition);

        JPanel addPanel = new JPanel(new BorderLayout());

        definitionsPanel= new JPanel();
        JScrollPane definitionScroll = new JScrollPane(definitionsPanel);
        definitionsPanel.setLayout(new BoxLayout(definitionsPanel, BoxLayout.PAGE_AXIS));

        JButton addDefinitionButton = new JButton("Add more definition");
        addDefinitionButton.addActionListener(e -> {
            int currentFields = definitionFields.size();
            TextLabelWithDestroyButton field = new TextLabelWithDestroyButton("Definition " + currentFields, "Delete", i -> {
                definitionsPanel.remove(i);
                definitionFields.remove(i);
                definitionsPanel.updateUI();
            });
            definitionFields.add(field);
            definitionsPanel.add(field);
            definitionsPanel.updateUI();
            //definitionsPanel.repaint();
        });

        JButton addButton = new JButton("Add new slang word (ignore the empty definition rows)");
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            //Get input definitions from the fields
            ArrayList<String> insertDef = new ArrayList<>(definitionFields.size());
            definitionFields.forEach(def -> {
                String text = def.getTextField().getText();
                if (text != null && !text.isBlank())
                    insertDef.add(text);
            });

            if (insertDef.size() == 0)
            {
                JOptionPane.showMessageDialog(this, "There is no definition for the slang word!", "The definition is empty!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SlangWord updateInformation = new SlangWord(slangPanel.getTextField().getText(), insertDef);
            boolean add = onAddSlang.test(updateInformation, () -> {
                int choice = JOptionPane.showOptionDialog(this,
                        "The slang word has already existed\n" +
                                "Do you want to overwrite it?",
                        "Confirm overwriting the slang word",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Yes", "No"},
                        0);
                return choice == 0;
            });

            String msg = add ? "Added the slang word successfully" : "Failed to add the new slang word";
            JOptionPane.showMessageDialog(this, msg);

            slangPanel.getTextField().setText(null);
            oldDefinition.getTextField().setText(null);
            definitionFields.clear();
            definitionsPanel.removeAll();
            definitionsPanel.updateUI();
        });

        addPanel.add(addDefinitionButton, BorderLayout.PAGE_START);
        addPanel.add(definitionScroll, BorderLayout.CENTER);
        addPanel.add(addButton, BorderLayout.PAGE_END);
        mainPanel.add(addPanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
