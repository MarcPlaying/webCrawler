import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {

    public String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/536.30.1 (KHTML, like Gecko) Version/6.0.5 Safari/536.30.1";

    public Crawler(String file) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            // Check if domain is crawled
            String[] slicedCrawl = line.split("\"");
            String domain = slicedCrawl[3];
            double score = Double.parseDouble(slicedCrawl[5]);

            ResultSet testIfValid = MySQL.Query("SELECT * FROM `sites` WHERE `domain` = ?", domain);
            try {
                if (testIfValid.next()) {

                } else {
                    try {
                        Document doc = Jsoup.connect("https://" + domain).userAgent(userAgent)
                                .timeout(10000)
                                .followRedirects(true)
                                .get();

                        String title = "";
                        String description = "";

                        Element descriptionMetaTag = doc.select("meta[name=description]").first();
                        description = descriptionMetaTag.attr("content");
                        title = doc.title();

                        MySQL.Exec(
                                "INSERT INTO `sites`(`domain`, `score`, `ver`, `description`, `title`) VALUES (?,?,?,?,?)",
                                domain, score + "", "1.0", description, title);
                        System.out.println(Prefix.INFO + "Crawled " + title + " / " + description + " / " + score);

                    } catch (Exception e) {
                        System.out.println(Prefix.ERROR + "Failed to crawl " + domain + " / " + score);
                    }
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
           
        }
        br.close();
    }
}
