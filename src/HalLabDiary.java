import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HalLabDiary {

    private static final String DIARYURL = "../../Downloads/hallab.html";

    public static void main(String[] args) {
        HalLabEntry entry = new HalLabEntry(DIARYURL);
        System.out.println(entry.getBody());
    }
}