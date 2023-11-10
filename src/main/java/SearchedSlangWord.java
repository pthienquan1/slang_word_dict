import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Phan Thiên Quân - 19127527
 * Date 11/10/2023 - 4:25 PM
 * Description: ...
 */
public class SearchedSlangWord extends SlangWord implements Serializable {
    private final LocalDateTime time;
    public SearchedSlangWord(String word, List<String> definition, LocalDateTime time)
    {
        super(word, definition);
        this.time = time;
    }

    public SearchedSlangWord(SlangWord slangWord)
    {
        super(slangWord.word, slangWord.definition);
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return time.format(formatter) + ": " + super.toString();
    }
}
