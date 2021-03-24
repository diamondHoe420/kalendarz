import java.sql.Time;
import java.util.Date;

public class Event {
    private Date date;
    private String name;
    private String desc;
    private Date updatedAt;

    Event(Date date, String name, String desc) {
        this.setDate(date);
        this.setName(name);
        this.setDescription(desc);
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return "Event{" +
                "date=" + getDate() +
                ", name='" + getName() + '\'' +
                ", desc='" + getDescription() + '\'' +
                '}';
    }

    public String getDataRow() {
        return updatedAt + ";" + getDate() + ";\t" + getName() + ";\t" + getDescription() + '\n';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }
}
