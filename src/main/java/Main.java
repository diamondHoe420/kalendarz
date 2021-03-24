import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        long startTime = Instant.now().toEpochMilli();
        ArrayList<Event> events = CalendarWebScraper.scrapeData();
        long endTime = Instant.now().toEpochMilli();
        long timeElapsed = endTime - startTime;

        System.out.println("DANE POBRANO W " + timeElapsed/1000d + " SEKUND");

        EventDatabaseWriter.initialize();

        Scanner scan = new Scanner(System.in);

        mainLoop:
        while (true) {

            System.out.println("WYBIERZ AKCJĘ");
            System.out.println("1 - ZAPISZ DANE W BAZIE DANYCH");
            System.out.println("2 - WYŚWIETL DANE Z BAZY DANYCH");
            System.out.println("3 - ZAPISZ DANE DO PLIKU");
            System.out.println("4 - WYJDŹ");


            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    EventDatabaseWriter.clearEventDataFromDatabase();
                    EventDatabaseWriter.writeToEventsDatabase(events);
                    System.out.println("BAZA DANYCH UAKTUALNIONA");
                    break;
                case 2:
                    EventDatabaseWriter.displayEventDatabaseData();
                    break;
                case 3:
                    WriteEventsToFile(events);
                    System.out.println("ZAPISANO W PLIKU ŚWIĘTA.TXT");
                    break;
                case 4:
                    break mainLoop;
                default:
                    System.out.println("BŁĘDNY KOD AKCJI");
                    break;
            }
        }
        scan.close();
    }


    private static void WriteEventsToFile(ArrayList<Event> events) {

        String csvFileContents = "Data pobrania, Data,Nazwa Święta,Opis\n";
        for(Event o : events) {
            csvFileContents += o.getDataRow();
        }

        try {
            FileWriter myWriter = new FileWriter("święta.txt");
            myWriter.write(csvFileContents);
            myWriter.close();
        } catch (IOException e) {  e.printStackTrace(); }
    }

}
