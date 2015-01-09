import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HalLabEntry {

    private String mTitle;
    private String mDate;
    private String mAuthor;
    private String mBody;

    HalLabEntry(String EntryUrl) {

        try {
            Parser parser = new Parser(EntryUrl);
            NodeFilter titleFilter = new HasAttributeFilter("CLASS", "diarytitle");
            NodeList title = parser.parse(titleFilter);
            mTitle = title.elementAt(0).getFirstChild().getText();

            parser.reset();
            parser.setResource(EntryUrl);
            NodeFilter bodyFilter = new HasAttributeFilter("CLASS", "diarybody");
            NodeList body = parser.parse(bodyFilter);
            Node node = body.elements().nextNode().getFirstChild();

            StringBuilder sb = new StringBuilder();
            while (node.getNextSibling() != null) {
                if (!node.getNextSibling().getText().contains("br")) {
                    sb.append(node.getNextSibling().getText());
                }
                node = node.getNextSibling();
            }
            mBody = sb.toString();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        return mBody;
    }
}
