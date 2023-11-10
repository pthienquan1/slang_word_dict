import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 4:13 PM
 * Description: ...
 */
public class SearchDefinitionPanel extends SearchPanel{
    public SearchDefinitionPanel(String title, String searchDisplay, Function<String, String> onSearch, Function<String, String> onSearchSaving)
    {
        super(title, searchDisplay, onSearch, onSearchSaving);

        resultText = new JTextField();
        resultText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        resultText.setEditable(false);
        addHintWhenInputting();

        mainPanel.add(resultText);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
