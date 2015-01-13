import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class HalLabDiary {

    private static final String DIARYURL   = "http://www.hallab.co.jp/diary/";
    private static final String DIARYINDEX = DIARYURL + "index.php";

    public static void main(String[] args) {

        if (args[0].equals("help")) {
            usage();
            System.exit(0);
        }

        Node node = getParentNode(DIARYINDEX);

        // show the latest entry
        if (args.length == 0) {
            HalLabEntry latestEntry = new HalLabEntry(getEntryUrl(node));
            printEntry(latestEntry);
        } else {
            String[] diaryDetails = new String[10];
            String[] titles = new String[10];
            String[] dates = new String[10];
            String[] authorsAndJobs = new String[10];
            for (int i = 0; i < 10; ++i) {
                diaryDetails[i] = getEntryUrl(node);
                titles[i] = getTitle(node);
                dates[i] = getDate(node);
                authorsAndJobs[i] = getAuthorAndJob(node);

                node = node.getNextSibling().getNextSibling();
            }

            if (args[0].equals("title")) {
                printTitles(titles, dates, authorsAndJobs, 10);
            } else {
                try {
                    int id = Integer.parseInt(args[0]);
                    String entryIn = diaryDetails[id];
                    HalLabEntry entry = new HalLabEntry(entryIn);
                    printEntry(entry);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 引数のURLのHTMLソースを取得する
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

    private static void printEntry(HalLabEntry entry) {
        System.out.println("[Title ] " + entry.getTitle());
        System.out.println("[Date  ] " + entry.getDate());
        System.out.println("[Author] " + entry.getAuthor() + "(" + entry.getJob() + ")");
        System.out.print(entry.getBody());
    }

    private static void printTitles(String[] titles, String[] dates, String[] authorsAndJobs, int n) {
        for (int i = 0; i < n; ++i) {
            System.out.print("(" + i + ")");
            System.out.println(authorsAndJobs[i] + " [ " + titles[i] + " ] ( " + dates[i] + ")");
        }
    }

    private static void usage() {
        System.out.println("Usage: java HalLabDiary <option>");
        System.out.println("");
        System.out.println("Description:");
        System.out.println("ハル研究所の日記を見ることが出来ます。\n" +
                "引数なしでは、最新の日記が表示されます。");
        System.out.println("");
        System.out.println("Option:");
        System.out.println("[0-9]: 指定した番目の記事を表示する。");
        System.out.println("title: タイトル一覧の表示");
    }

    // url内に記述されている日記のタイトルを取得するための
    // 親となるNodeを取得する
    //
    // Note: HTMLソース全体の親ノードではない
    private static Node getParentNode(String url) {
        try {
            Parser parser = new Parser(htmlSource(url));
            NodeList nodeList = parser.parse(new HasAttributeFilter("clear", "all"));
            return nodeList.elementAt(0).getNextSibling().getNextSibling();
        } catch (ParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getEntryUrl(Node parent) {
        String detail = Arrays.asList(parent.getFirstChild().getText().split("/")).get(1);
        return DIARYURL + detail.substring(0, detail.length() - 1);
    }

    private static String getTitle(Node parent) {
        return parent.getFirstChild().getFirstChild().getText();
    }

    private static String getDate(Node parent) {
        return parent.getNextSibling().getFirstChild().getFirstChild().getText();
    }

    private static String getAuthorAndJob(Node parent) {
        return parent.getNextSibling().getFirstChild().getNextSibling().getFirstChild().getText();
    }
}