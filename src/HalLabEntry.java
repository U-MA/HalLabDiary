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
            NodeList list = parser.parse(new HasAttributeFilter(CLASS, TITLEVALUE));
            mTitle  = list.elementAt(0).getFirstChild().getText();
            mDate   = list.elementAt(0).getNextSibling().getFirstChild().getFirstChild().getText();
            mAuthor = list.elementAt(0).getNextSibling().getFirstChild().getNextSibling().getFirstChild().getText();

            Node node = list.elementAt(0).getNextSibling().getNextSibling().getFirstChild().getNextSibling();
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
