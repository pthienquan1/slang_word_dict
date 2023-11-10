import javax.swing.*;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 3:58 PM
 * Description: ...
 */
public class SearchSlangWordPanel extends SearchPanel{
    public SearchSlangWordPanel(String title, String searchDisplay, Function<String, String> onSearch, Function<String, String> onSearchSaving)
    {
        super(title, searchDisplay, onSearch, onSearchSaving);

        resultText = new JTextArea();
        JScrollPane resultScroll = new JScrollPane(resultText);
        resultText.setEditable(false);
        addHintWhenInputting();

        mainPanel.add(resultScroll);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
