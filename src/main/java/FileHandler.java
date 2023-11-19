import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/19/2023 - 12:32 PM
 * Description: File Handler
 */
public class FileHandler {
    private static final String STRING_PATH = "slang.txt";
    public static void saveSlangWordsToFile(Map<String, SlangWord> slangWordMap) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(STRING_PATH))) {
            for (SlangWord slangWord : slangWordMap.values()) {
                printWriter.print(slangWord.getWord() + "`");
                printWriter.println(String.join("|", slangWord.getDefinition()));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while saving slang words to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Map<String, SlangWord> loadSlangWordsFromFile() {
        Map<String, SlangWord> slangWords = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(STRING_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("`");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String[] definitions = parts[1].split("\\|");
                    List<String> definitionList = new ArrayList<>();
                    for (String def : definitions) {
                        definitionList.add(def.trim());
                    }
                    slangWords.put(word, new SlangWord(word, definitionList));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return slangWords;
    }
}
