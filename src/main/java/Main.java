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
            String pUrl = el.getElementsByTag("a").attr("href");
            if (pUrl=="") {
                continue;
            }
            HandleDateData(pUrl);
        }
    }

    public static void HandleDateData(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return;
        }


        try {
            Element events = doc.getElementsByClass("calCard-ententa").first();
            for (Element event : events.getElementsByTag("a") ) {
                System.out.println(event.text());
                String desc = GetEventDescription( event.attr("href") );
                if (desc!="")
                    System.out.println(desc);
            }
        } catch (NullPointerException e) {
            return;
        }
    }

    public static String GetEventDescription(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return "";
        }
        Element el = doc.getElementById("opis_nt");
        if (el!=null) {
            return el.text();
        }
        return "";
    }
}
