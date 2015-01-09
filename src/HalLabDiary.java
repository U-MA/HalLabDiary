public class HalLabDiary {

    private static final String DIARYURL = "../../Downloads/hallab.html";

    public static void main(String[] args) {
        HalLabEntry entry = new HalLabEntry(DIARYURL);
        System.out.println(entry.getBody());
    }
}