import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParagraphCount {
   String text;
    public ParagraphCount(String para){

        text=para;
    }
    public int paragraphCounter() {

        String paragraphDelimiter = "\\n\\s*\\n";

        // Split the text into paragraphs based on the delimiter
        String[] paragraphs = text.split(paragraphDelimiter);


        int paragraphCount = paragraphs.length;


        return paragraphCount;
    }

}
