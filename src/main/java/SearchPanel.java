import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.function.Function;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 3:26 PM
 * Description: ...
 */
public class SearchPanel extends MenuPanel{

    protected final TextWithLabel searchPanel;

    protected JTextComponent resultText;

    protected final JButton searchButton;

    protected final Function<String, String> onSearch;


    public SearchPanel(String title, String searchDisplay, Function<String, String> onSearch, Function<String, String> onSearchSaving)
    {
        super(title);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        searchPanel = new TextWithLabel(searchDisplay);

        JLabel resultLabel = new JLabel("Result");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        searchButton = new JButton("Save search history");
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        this.onSearch = onSearch;

        searchButton.addActionListener(e -> {
            if (!searchPanel.getTextField().getText().isBlank())
            {
                String result = onSearchSaving.apply(searchPanel.getTextField().getText());
                SwingUtilities.invokeLater(() -> resultText.setText(result));
            }
        });

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(searchPanel);
        mainPanel.add(searchButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(resultLabel);
    }

    protected void addHintWhenInputting()
    {
        //Add hints when inputting new text
        searchPanel.getTextField().getDocument().addDocumentListener(new HintForInput(searchPanel.getTextField(), resultText, onSearch));
    }
}
