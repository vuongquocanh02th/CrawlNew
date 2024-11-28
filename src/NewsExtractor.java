import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsExtractor {

    // Phương thức để lấy mã HTML từ URL
    public static String getHTMLFromURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Đọc nội dung của trang web
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        return stringBuilder.toString();
    }

    // Phương thức để trích xuất các bản tin từ mã HTML sử dụng Regular Expression
    public static void extractNews(String html) {
        // Biểu thức chính quy để tìm các tiêu đề bản tin (giả sử chúng nằm trong thẻ <h2>)
        // Chúng ta sẽ giả sử các bản tin có cấu trúc trong thẻ <article> hoặc <div class="news-item">
        String regex = "<h2[^>]*>(.*?)</h2>\\s*<p[^>]*>(.*?)</p>";

        // Compile biểu thức chính quy
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        // Kiểm tra và in ra các bản tin tìm được
        while (matcher.find()) {
            String title = matcher.group(1);  // Tiêu đề
            String description = matcher.group(2);  // Mô tả

            System.out.println("Tiêu đề: " + title);
            System.out.println("Mô tả: " + description);
            System.out.println("----------------------------");
        }
    }

    public static void main(String[] args) {
        String url = "https://example.com/news";  // Thay bằng URL thực tế của trang bạn muốn lấy thông tin

        try {
            // Lấy mã HTML từ URL
            String html = getHTMLFromURL(url);

            // Trích xuất và hiển thị danh sách các bản tin
            extractNews(html);
        } catch (IOException e) {
            System.out.println("Có lỗi khi tải trang web: " + e.getMessage());
        }
    }
}
