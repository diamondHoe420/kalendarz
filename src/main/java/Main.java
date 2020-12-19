import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static ArrayList<Event> events = new ArrayList<Event>();

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

        for (Event e : events) {
            System.out.println(e);
        }
    }

    private static void HandleDateData(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return;
        }


        try {
            Element eventElements = doc.getElementsByClass("calCard-ententa").first();
            for (Element eventElement : eventElements.getElementsByTag("a") ) {
                String name = eventElement.text();
                String desc = GetEventDescription( eventElement.attr("href") );
//                if (desc!="")
//                    System.out.println(desc);

                String date = doc.getElementById("data").val();


                Event event = new Event( date, name, desc );
                events.add(event);
            }
        } catch (NullPointerException e) {
            return;
        }
    }

    private static String GetEventDescription(String url) {
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
