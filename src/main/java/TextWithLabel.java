import javax.swing.*;
import java.awt.*;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/9/2023 - 9:18 PM
 * Description: ...
 */
public class TextWithLabel extends JPanel {
    private final JTextField textField;
    protected final JPanel mainPanel;
    public JTextField getTextField() {
        return textField;
    }
    public TextWithLabel(String displayedOnLabel)
    {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel(displayedOnLabel);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label, BorderLayout.PAGE_START);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        textField = new JTextField();
        mainPanel.add(textField);
        add(mainPanel, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

}
