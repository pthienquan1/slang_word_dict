import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.*;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 4:02 PM
 * Description: ...
 */
public class ProgramFrame extends JFrame {
    private final String SEARCH_DEFINITION = "Search definition by slang word";
    private final String SEARCH_SLANG = "Search slang words by definition";
    private final String GET_HISTORY = "Get search history";
    private final String ADD_SLANG = "Add new slang word";
    private final String UPDATE_SLANG = "Update a slang word";
    private final String DELETE_SLANG = "Delete a slang word";
    private final String SETTING = "Settings";
    private final String RANDOM = "Random a slang word";
    private final String PUZZLES = "Puzzles";
    private  final String PUZZLE_SLANG = "Guess definition by slang word";
    private  final String PUZZLE_DEFINITION = "Guess slang word by definition";
//    private final Runnable onSave;

    public ProgramFrame(Function<String, String> onSearchDefinitionSaving,
                        Function<String, String> onSearchDefinition,
                        Function<String, String> onSearchSlangSaving,
                        Function<String, String> onSearchSlang,
                        Supplier<List<String>> onGetSearchDefinitionHistory,
                        Supplier<java.util.List<String>> onGetSearchSlangHistory,
                        List<SearchedSlangWord> definitionLoadedHistory,
                        List<SearchedSlangWord> slangLoadedHistory)
//                        BiPredicate<SlangWord, BooleanSupplier> onAddSlangWord,
//                        Consumer<SlangWord> onUpdate, Consumer<String> onDelete, Runnable onReset, Runnable onSave,
//                        Supplier<SlangWord> onRandom, Supplier<Question> onSlangPuzzle, Supplier<Question> onDefinitionPuzzle)
    {
        JTabbedPane mainPanel = new JTabbedPane();
        mainPanel.setFont(new Font( "Arial", Font.BOLD, 15 ));
        mainPanel.setTabPlacement(JTabbedPane.LEFT);
        //Create function panels
        mainPanel.add(SEARCH_DEFINITION, new SearchDefinitionPanel(SEARCH_DEFINITION, "Slang", onSearchDefinition, onSearchDefinitionSaving));
        mainPanel.add(SEARCH_SLANG, new SearchSlangWordPanel(SEARCH_SLANG, "Definition", onSearchSlang, onSearchSlangSaving));
        mainPanel.add(GET_HISTORY, new GetHistoryPanel(GET_HISTORY, onGetSearchDefinitionHistory, onGetSearchSlangHistory, definitionLoadedHistory, slangLoadedHistory));
//        mainPanel.add(ADD_SLANG, new AddSlangWordPanel(ADD_SLANG, onSearchDefinition, onAddSlangWord));
//        mainPanel.add(UPDATE_SLANG, new UpdateSlangWordPanel(UPDATE_SLANG, onSearchDefinition, onUpdate));
//        mainPanel.add(DELETE_SLANG, new DeleteSlangWordPanel(DELETE_SLANG, onSearchDefinition, onDelete));
//        mainPanel.add(RANDOM, new RandomSlangWordPanel(RANDOM, onRandom));
//
//        JTabbedPane puzzleTab = new JTabbedPane();
//        puzzleTab.add(PUZZLE_SLANG, new PuzzlePanel(PUZZLE_SLANG, onSlangPuzzle));
//        puzzleTab.add(PUZZLE_DEFINITION, new PuzzlePanel(PUZZLE_DEFINITION, onDefinitionPuzzle));
//        mainPanel.add(PUZZLES, puzzleTab);
//
//        mainPanel.add(SETTING, new SettingPanel(SETTING, onReset, onSave));
        this.getContentPane().add(mainPanel);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.onSave = onSave;
        pack();
        setVisible(true);
    }

    @Override
    public void dispose()
    {
        //Saving the data before closing the program
//        onSave.run();
        super.dispose();
    }
}
