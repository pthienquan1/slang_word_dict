import javax.swing.*;
import java.awt.*;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/9/2023 - 9:00 PM
 * Description: ...
 */
public class MenuPanel extends JFrame {
    protected JPanel mainPanel;

    public MenuPanel(String title)
    {
        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        labelPanel.add(titleLabel);

        mainPanel = new JPanel();

        add(labelPanel, BorderLayout.PAGE_START);
        add(mainPanel);
    }
}
