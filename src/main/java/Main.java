import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String url = "https://www.kalbi.pl/kalendarz-swiat-nietypowych-2020";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return;
        }

        Elements days = doc.getElementsByClass("festCal_d");
        for (Element el : days) {
            System.out.println(el.getElementsByTag("a").attr("title"));
        }
    }
}
