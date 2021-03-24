import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class EventDatabaseWriter {
    private static Connection connection;
    public static void initialize() {
        String url = "jdbc:mysql://localhost:3306/calendar";
        String user = "root";
        try  {
            connection = DriverManager.getConnection(url, user, "");
            Statement myStmt = connection.createStatement();
            myStmt.executeUpdate("DELETE FROM events WHERE 1;");

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void clearEventDataFromDatabase() {
        if (connection!=null) {
            try {
                Statement myStmt = connection.createStatement();
                myStmt.executeUpdate("DELETE FROM events WHERE 1;");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public static void displayEventDatabaseData() {
        if (connection!=null) {
            try {
                Statement myStmt = connection.createStatement();
                ResultSet rs = myStmt.executeQuery("SELECT * FROM events");
                while(rs.next()){
                    java.sql.Date date = rs.getDate("date");
                    String name = rs.getString("name");
                    String desc = rs.getString("description");

                    System.out.println(String.format("%s | %s | %s", date, name, desc));
                }
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public static void writeToEventsDatabase(ArrayList<Event> events) {
        if (connection!=null) {
            for (Event event : events) {
                try  {
                    Statement myStmt = connection.createStatement();
                    String sql = "insert into events (name, date, description) values ";
                    Date date  = event.getDate();
                    String dateStr = date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
                    String escapedName = event.getName().replace("\\", "\\\\")
                                                        .replace("\t", "\\t")
                                                        .replace("\b", "\\b")
                                                        .replace("\n", "\\n")
                                                        .replace("\r", "\\r")
                                                        .replace("\f", "\\f")
                                                        .replace("\'", "\\'")
                                                        .replace("\"", "\\\"");
                    String escapedDesc = event.getDescription().replace("\\", "\\\\")
                                                                .replace("\t", "\\t")
                                                                .replace("\b", "\\b")
                                                                .replace("\n", "\\n")
                                                                .replace("\r", "\\r")
                                                                .replace("\f", "\\f")
                                                                .replace("\'", "\\'")
                                                                .replace("\"", "\\\"");
                    sql+=String.format("('%s', '%s', '%s' );", escapedName, dateStr, escapedDesc);
                    myStmt.executeUpdate(sql);
                } catch (SQLException se) {
                    se.printStackTrace();
                    continue;
                }
            }
        }
    }
}
