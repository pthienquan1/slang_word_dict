import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 4:24 PM
 * Description: ...
 */
public class GetHistoryPanel extends MenuPanel{
    private final JList searchSlangHistoryList;

    private final JList searchDefinitionHistoryList;

    public GetHistoryPanel(String title, Supplier<List<String>> onViewDefinitionHistory,
                           Supplier<List<String>> onViewSlangHistory, List<SearchedSlangWord> loadedDefinitionHistory,
                           List<SearchedSlangWord> loadedSlangHistory)
    {
        super(title);
        mainPanel.setLayout(new BorderLayout());
        JTabbedPane mainTab = new JTabbedPane();

        JButton loadButton = new JButton("Load");

        searchDefinitionHistoryList = new JList();
        JScrollPane definitionScroll = new JScrollPane(searchDefinitionHistoryList);
        searchDefinitionHistoryList.setListData(loadedDefinitionHistory.toArray());

        searchSlangHistoryList = new JList();
        JScrollPane slangScroll = new JScrollPane(searchSlangHistoryList);
        searchSlangHistoryList.setListData(loadedSlangHistory.toArray());

        loadButton.addActionListener(e -> {
            List<String> defHistory = onViewDefinitionHistory.get();
            if (defHistory != null)
            {
                SwingUtilities.invokeLater(() -> searchDefinitionHistoryList.setListData(defHistory.toArray()));
            }

            List<String> slangHistory = onViewSlangHistory.get();
            if (slangHistory != null)
            {
                SwingUtilities.invokeLater(() -> searchSlangHistoryList.setListData(slangHistory.toArray()));
            }
        });

        mainTab.add("Search definition history", definitionScroll);
        mainTab.add("Search slang words history", slangScroll);
        mainPanel.add(loadButton, BorderLayout.PAGE_START);
        mainPanel.add(mainTab, BorderLayout.CENTER);
    }
}
