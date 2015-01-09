import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HalLabDiary {

    private static final String DIARYURL = "../../Downloads/hallab.html";
    private static final String TITLEVALUE = "diarytitle";
    private static final String BODYVALUE  = "diarybody";

    private static void printDiaryBody(Parser parser) {
        try {
            NodeList list = parser.parse(new HasAttributeFilter("class", TITLEVALUE));
            System.out.println("Title:");
            System.out.println(list.elements().nextNode().getFirstChild().getText());
            System.out.println();

            parser.reset();
            parser.setResource(DIARYURL);
            list = parser.parse(new HasAttributeFilter("class", BODYVALUE));
            Node node = list.elements().nextNode().getFirstChild();

            System.out.println("Body:");
            while (node.getNextSibling() != null) {
                if (!node.getNextSibling().getText().contains("br")) {
                    System.out.print(node.getNextSibling().getText());
                }
                node = node.getNextSibling();
            }
        } catch (EncodingChangeException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser(DIARYURL);
            parser.setEncoding("UTF-8");
            printDiaryBody(parser);
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }
}