import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/9/2023 - 9:33 PM
 * Description: ...
 */
public record HintForInput(JTextField slangField, JTextComponent oldDefinition, Function<String, String> onGetDefinition) implements DocumentListener {
    private void updateDefinition()
    {
        try
        {
            String text = slangField.getText();
            if (text != null && !text.isBlank())
            {
                //Get hints
                String definition = onGetDefinition.apply(text);
                //Set hints to the result field
                SwingUtilities.invokeLater(() -> oldDefinition.setText(definition));
            }
            else
            {
                //Set null to the result field if the user inputs nothing
                SwingUtilities.invokeLater(() -> oldDefinition.setText(null));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        updateDefinition();
    }


    @Override
    public void removeUpdate(DocumentEvent e) {
        updateDefinition();
    }


    @Override
    public void changedUpdate(DocumentEvent e) {
        updateDefinition();
    }

}
