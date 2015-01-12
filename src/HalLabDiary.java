import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class HalLabDiary {

    private static final String DIARYURL = "http://www.hallab.co.jp/diary/index.php";
    private static final String DIARYDETAIL = "http://www.hallab.co.jp/diary/";

    public static void main(String[] args) {
        try {
            String parserIn = htmlSource(DIARYURL);

            Parser parser = new Parser(parserIn);
            NodeList nodeList = parser.parse(new HasAttributeFilter("clear", "all"));
            TagNode tag = (TagNode) nodeList.elementAt(0).getNextSibling().getNextSibling();
            String detail = Arrays.asList(tag.getFirstChild().getText().split("/")).get(1);
            String diaryUrl = DIARYDETAIL + detail.substring(0, detail.length()-1);
            System.out.println(tag.getFirstChild().getFirstChild().getText()); /// title
            System.out.println(tag.getNextSibling().getFirstChild().getFirstChild().getText()); // date

            // author and job
            System.out.println(tag.getNextSibling().getFirstChild().getNextSibling().getFirstChild().getText());

            // entry url
            System.out.println(diaryUrl);

            String entryIn = htmlSource(diaryUrl);
            HalLabEntry entry = new HalLabEntry(entryIn);
            System.out.println(entry.getBody());
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    private static String htmlSource(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "euc-jp"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                String tmp = line + "\n";
                response.append(tmp);
            }
            in.close();

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}