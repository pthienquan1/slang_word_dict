import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/9/2023 - 8:02 PM
 * Description: Slang word dictionary
 */
public class SlangWord {
    protected String word;
    protected List<String> definition;

    public SlangWord(String word, List<String> definition)
    {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getDefinition() {
        return definition;
    }

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "SlangWord{" +
                "word='" + word + '\'' +
                ", definition=" + definition +
                '}';
    }
    public static String asString(List<String> parts, String splitter)
    {
        try
        {
            StringBuilder format = new StringBuilder();
            for (String slang : parts)
                format.append(slang).append(splitter);
            //Delete the last char
            format.deleteCharAt(format.length() - 1);
            return format.toString();
        }
        catch (Exception e)
        {
            return null;
        }

    }

}
