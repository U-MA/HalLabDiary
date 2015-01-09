public class HalLabDiary {

    private static final String DIARYURL = "../../Downloads/hallab.html";

    public static void main(String[] args) {
        HalLabEntry entry = new HalLabEntry(DIARYURL);
        System.out.println("Date: " + entry.getDate() + "\nTitle: " + entry.getTitle());
        System.out.println("Author: " + entry.getAuthor() + "( " + entry.getJob() + " )");
        System.out.println(entry.getBody());

    }
}