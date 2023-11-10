import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 1:50 PM
 * Description: ...
 */
public class TextLabelWithDestroyButton extends TextWithLabel {
    public TextLabelWithDestroyButton(String displayedOnLabel, String displayedOnButton, Consumer<Component> onDestroy)
    {
        super(displayedOnLabel);
        JButton button = new JButton(displayedOnButton);
        button.addActionListener(e -> onDestroy.accept(this));
        mainPanel.add(button);
    }
}
