import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 2:04 PM
 * Description: ...
 */
public class ProgramManagementSystem {
    private final String SAVE_PATH = "save.dat";
    private final String HISTORY_DEFINITION_PATH = "historyDef.dat";
    private final String HISTORY_SlANG_PATH = "historySlang.dat";
    private ModifyMap data;
    private ArrayList<SearchedSlangWord> searchedDefinitions;
    private ArrayList<SearchedSlangWord> searchedSlangWords;
    private static ProgramManagementSystem instance;

    public static ProgramManagementSystem getInstance()
    {
        if (instance == null)
            instance = new ProgramManagementSystem();
        return instance;
    }
    private ProgramManagementSystem()
    {
        firstLoad();

        Function<String, String> onSearchDefinition = (s)->{
            List<String> result = data.getSlangMap().get(s);
            if (result != null)
            {
                searchedDefinitions.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
                return SlangWord.asString(result, "|");
            }
            return null;
        };

        //Search slang word and save history callback
        Function<String, String> onSeachSlangWords = (s) ->
        {
            List<String> result = data.getDefinitionMap().get(s);
            if (result != null)
            {
                searchedSlangWords.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
                return SlangWord.asString(result, "\n");
            }
            return null;
        };

        //Get search definition history callback
        Supplier<List<String>> onGetDefinitionHistory = () ->
        {
            try
            {
                List<String> words = new ArrayList<>(searchedDefinitions.size());
                for (SearchedSlangWord w : searchedDefinitions)
                    words.add(w.toString());
                return words;
            }
            catch (Exception e)
            {
                return null;
            }
        };

        //Get search slang word history callback
        Supplier<List<String>> onGetSlangHistory = () ->
        {
            try
            {
                List<String> words = new ArrayList<>(searchedSlangWords.size());
                for (SearchedSlangWord w : searchedSlangWords)
                    words.add(w.toString());
                return words;
            }
            catch (Exception e)
            {
                return null;
            }
        };

        //Create the main frame
        new ProgramFrame(onSearchDefinition, s -> SlangWord.asString(data.getSlangMap().get(s), "|"), onSeachSlangWords, s -> SlangWord.asString(data.getDefinitionMap().get(s), "\n"), onGetDefinitionHistory, onGetSlangHistory, searchedDefinitions, searchedSlangWords);
    }


    private void saveData()
    {
        try
        {
            saveObject(data, SAVE_PATH);
            saveObject(searchedDefinitions, HISTORY_DEFINITION_PATH);
            saveObject(searchedSlangWords, HISTORY_SlANG_PATH);
        }
        catch (Exception ignored)
        {

        }
    }

    private void firstLoad()
    {
        try
        {
            Object map = loadObject(SAVE_PATH);
            //If the save file does not exist
            if (map == null)
            {
                data = new ModifyMap();
                String ORIGINAL_PATH = "slang.txt";
                data.load(ORIGINAL_PATH);
            }
            else
                data = (ModifyMap) map;

            Object defHistory = loadObject(HISTORY_DEFINITION_PATH);
            searchedDefinitions = defHistory == null ? new ArrayList<>() : (ArrayList<SearchedSlangWord>)defHistory;

            Object slangHistory = loadObject(HISTORY_SlANG_PATH);
            searchedSlangWords = slangHistory == null ? new ArrayList<>() : (ArrayList<SearchedSlangWord>)slangHistory;

        }
        catch (Exception ignored)
        {
        }
    }


    private Object loadObject(String path)
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));
            return stream.readObject();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private void saveObject(Serializable serializable, String path)
    {
        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path));
            stream.writeObject(serializable);
            stream.flush();
            stream.close();
        }
        catch (Exception e)
        {

        }
    }
    public static void main(String[] args){
        ProgramManagementSystem.getInstance();
    }
}
