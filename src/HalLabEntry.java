import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HalLabEntry {

    private static final String CLASS      = "CLASS";
    private static final String TITLEVALUE = "diarytitle";

    private String mTitle;
    private String mDate;
    private String mAuthor;
    private String mJob;
    private String mBody;

    HalLabEntry(String EntryUrl) {

        try {
            Parser parser = new Parser(EntryUrl);
            NodeFilter titleFilter = new HasAttributeFilter(CLASS, TITLEVALUE);
            NodeList title = parser.parse(titleFilter);
            mTitle  = title.elementAt(0).getFirstChild().getText();
            mDate   = title.elementAt(0).getNextSibling().getFirstChild().getFirstChild().getText();
            mAuthor = title.elementAt(0).getNextSibling().getFirstChild().getNextSibling().getFirstChild().getText();

            Node node = title.elementAt(0).getNextSibling().getNextSibling().getFirstChild().getNextSibling();
            System.out.println(node.getText());

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

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getDate() {
        return mDate;
    }

    public String getAuthor() {
        return mAuthor;
    }

}
