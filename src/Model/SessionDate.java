package Model;

import java.util.List;

public class SessionDate {
    public String weekDay;
    public String date;
    public Session session;

    public SessionDate(String weekDay, String date, Session session) {
        this.weekDay = weekDay;
        this.date = date;
        this.session=session;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
