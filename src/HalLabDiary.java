import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HalLabDiary {

    private static final String DIARYURL_TEST = "../../Downloads/hallab.html";
    private static final String DIARYURL = "http://www.hallab.co.jp/diary/index.php";
    private static final String DIARYENTRY = "http://www.hallab.co.jp/diary/detail.php?id=630";

    public static void main(String[] args) {
        try {
            URL url = new URL(DIARYENTRY);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "euc-jp"));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line + "\n");
            }
            in.close();

            HalLabEntry entry = new HalLabEntry(response.toString());
            System.out.println("title : " + entry.getTitle());
            System.out.println("date  : " + entry.getDate());
            System.out.println("author: " + entry.getAuthor());
            System.out.println("body  : " + entry.getBody());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}