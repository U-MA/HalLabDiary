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
            URL url = new URL(DIARYURL);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "euc-jp"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                String tmp = line + "\n";
                response.append(tmp);
            }
            in.close();

            Parser parser = new Parser(response.toString());
            NodeList nodeList = parser.parse(new HasAttributeFilter("clear", "all"));
            TagNode tag = (TagNode) nodeList.elementAt(0).getNextSibling().getNextSibling();
            String detail = Arrays.asList(tag.getFirstChild().getText().split("/")).get(1);
            String diaryUrl = DIARYDETAIL + detail.substring(0, detail.length()-1);
            System.out.println(tag.getFirstChild().getFirstChild().getText());
            System.out.println(tag.getNextSibling().getFirstChild().getFirstChild().getText());
            System.out.println(tag.getNextSibling().getFirstChild().getNextSibling().getFirstChild().getText());

            System.out.println(diaryUrl);
            URL url1 = new URL(diaryUrl);
            URLConnection connection1 = url1.openConnection();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream(), "euc-jp"));
            StringBuilder response1 = new StringBuilder();
            String line1;
            while ((line1 = in1.readLine()) != null) {
                String tmp = line1 + "\n";
                response1.append(tmp);
            }
            in1.close();

            HalLabEntry entry = new HalLabEntry(response1.toString());
            System.out.println(entry.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }
}