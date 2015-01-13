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

        // show the latest entry
        if (args.length == 0) {
            try {
                String parseIn = htmlSource(DIARYURL);

                Parser parser = new Parser(parseIn);
                NodeList nodeList = parser.parse(new HasAttributeFilter("clear", "all"));
                TagNode tag = (TagNode) nodeList.elementAt(0).getNextSibling().getNextSibling();
                String detail = Arrays.asList(tag.getFirstChild().getText().split("/")).get(1);
                String entryUrl = DIARYDETAIL + detail.substring(0, detail.length() - 1);
                HalLabEntry latestEntry = new HalLabEntry(entryUrl);

                System.out.println("[Title ] " + latestEntry.getTitle());
                System.out.println("[Date  ] " + latestEntry.getDate());
                System.out.println("[Author] " + latestEntry.getAuthor() + "(" + latestEntry.getJob() + ")");
                System.out.print(latestEntry.getBody());
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
        else if (args[0].equals("help") ) {
            System.out.println("Usage: java HalLabDiary <option>");
            System.out.println("");
            System.out.println("Description:");
            System.out.println("ハル研究所の日記を見ることが出来ます。\n" +
                    "引数なしでは、最新の日記が表示されます。");
            System.out.println("");
            System.out.println("Option:");
            System.out.println("[0-9]: 指定した番目の記事を表示する。");
            System.out.println("title: タイトル一覧の表示");
        } else {

            try {
                String parserIn = htmlSource(DIARYURL);

                Parser parser = new Parser(parserIn);
                NodeList nodeList = parser.parse(new HasAttributeFilter("clear", "all"));
                TagNode tag = (TagNode) nodeList.elementAt(0).getNextSibling().getNextSibling();
                String detail = Arrays.asList(tag.getFirstChild().getText().split("/")).get(1);
                String diaryUrl = DIARYDETAIL + detail.substring(0, detail.length() - 1);
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