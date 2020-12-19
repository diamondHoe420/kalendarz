import java.util.Date;

public class Event {
    public String date;
    public String name;
    public String desc;

    Event(String date, String name, String desc) {
        this.date = date;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Event{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getDataRow() {
        return date + ";\t" + name + ";\t" + desc + '\n';
    }
}
