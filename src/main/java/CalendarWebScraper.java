import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Date;

import java.io.IOException;
import java.util.ArrayList;

public class CalendarWebScraper {
    private static final boolean DO_DEBUG_CONSOLE_LOGS = true;


    public static ArrayList<Event> scrapeData() {

        ArrayList<Event> events = new ArrayList<Event>();

        String url = "https://www.kalbi.pl/kalendarz-swiat-nietypowych-2020";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return events;
        }

        Elements days = doc.getElementsByClass("festCal_d");
        for (Element el : days) {
            String pUrl = el.getElementsByTag("a").attr("href");
            if (pUrl=="") {
                continue;
            }
            events.addAll(getAllEventsAtDate(pUrl));
        }
        return events;
    }

    private static ArrayList<Event> getAllEventsAtDate(String url) {
        ArrayList<Event> events = new ArrayList<Event>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            System.out.println("COULD NOT CONNECT TO " + url);
            return events;
        }


        try {
            Element eventElements = doc.getElementsByClass("calCard-ententa").first();
            for (Element eventElement : eventElements.getElementsByTag("a") ) {
                String name = eventElement.text();
                String desc = getEventDescription( eventElement.attr("href") );
                String dateString = doc.selectFirst("input.fallbackDatePicker").val();
                String[] dateComponents = dateString.split("-");

                Date date = new Date(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1])-1, Integer.parseInt(dateComponents[2]) );
                if (DO_DEBUG_CONSOLE_LOGS) {
                    System.out.println( date + " : " + name );
                }
                Event event = new Event( date, name, desc );
                events.add(event);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return events;
        }

        return events;
    }

    private static String getEventDescription(String url) {
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
