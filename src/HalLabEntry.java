import org.htmlparser.Node;
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
            Parser parser   = new Parser(EntryUrl);
            NodeList list   = parser.parse(new HasAttributeFilter(CLASS, TITLEVALUE));
            Node titleNode  = list.elementAt(0).getFirstChild();
            Node dateNode   = list.elementAt(0).getNextSibling().getFirstChild().getFirstChild();
            Node authorNode = list.elementAt(0).getNextSibling().getFirstChild().getNextSibling().getFirstChild();

            mTitle  = titleNode.getText();
            mDate   = dateNode.getText();

            String authorAndJob = authorNode.getText();
            int i = authorAndJob.indexOf('\t');
            int j = authorAndJob.lastIndexOf('\t');
            mAuthor = authorAndJob.substring(0, i-1);
            mJob    = authorAndJob.substring(j+2, authorAndJob.length());

            Node node = list.elementAt(0).getNextSibling().getNextSibling().getFirstChild().getNextSibling();

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

    public String getJob() {
        return mJob;
    }

}
