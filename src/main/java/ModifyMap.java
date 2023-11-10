import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BooleanSupplier;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 2:08 PM
 * Description: ...
 */
public class ModifyMap implements Serializable {

    private final TreeMap<String, List<String>> slangMap;

    private final TreeMap<String, List<String>> definitionMap;

    public ModifyMap() {
        slangMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        definitionMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    private void addDefinitions(List<String> definitions, String slangWord) {
        for (String def : definitions) {
            //Add new slang to the new definition
            List<String> slangWords = definitionMap.computeIfAbsent(def, k -> new ArrayList<>());
            //If the slang words list does not exist
            slangWords.add(slangWord);
        }
    }

    public void addNotCheck(String slang, List<String> definition) {
        slangMap.put(slang, definition);
        addDefinitions(definition, slang);
    }


    public boolean add(SlangWord slangWord, BooleanSupplier duplicatedCallback) {
        //overwrite the slang that have existed
        if ( slangMap.containsKey(slangWord.word) ) {
            //If user approves to overwrite then update this word
            return duplicatedCallback.getAsBoolean();
            //update(slangWord);
        } else
            addNotCheck(slangWord.word, slangWord.definition);
        return true;
    }
    public void load(String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            //Ignore the first line
            reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    String[] parts = line.split("`");
                    String[] defs = parts[1].split("\\|");
                    for (int i = 0; i < defs.length; i++)
                        defs[i] = defs[i].trim();
                    addNotCheck(parts[0], Arrays.stream(defs).toList());
                }
                catch (Exception ignored)
                {
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public TreeMap<String, List<String>> getSlangMap() {
        return slangMap;
    }

    public TreeMap<String, List<String>> getDefinitionMap() {
        return definitionMap;
    }
}
