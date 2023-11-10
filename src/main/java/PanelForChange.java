import javax.swing.*;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/9/2023 - 9:03 PM
 * Description: ...
 */
public class PanelForChange extends MenuPanel{
    protected final TextWithLabel slangPanel = new TextWithLabel("Slang word");
    protected final TextWithLabel oldDefinition = new TextWithLabel("Current definition");
    public PanelForChange(String title, Function<String, String> onGetDefinition)
    {
        super(title);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        slangPanel.getTextField().getDocument().addDocumentListener(new HintForInput(slangPanel.getTextField(), oldDefinition.getTextField(), onGetDefinition));
        oldDefinition.getTextField().setEditable(false);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(slangPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(oldDefinition);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
