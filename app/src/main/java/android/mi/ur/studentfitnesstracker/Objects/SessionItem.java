package android.mi.ur.studentfitnesstracker.Objects;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionItem {
    private GregorianCalendar cal;
    private int id;
    private int time;
    Calculator calc;

    public SessionItem(int id, int day, int month, int year) {
        this.id = id;
        cal = new GregorianCalendar(day, month, year);
    }

    public int getId() {
        return id;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
        return df.format(cal.getTime());
    }

    public Date getTime() {
        return cal.getTime();
    }


}
