package android.mi.ur.studentfitnesstracker.Objects;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionItem {
    String sessionType;
    String pace; // pace in min/km
    int distance; // distance in meters
    long time; //Time in seconds
    double kCal;
    String date;

    public SessionItem(String sessionType, int distance, long time, double kCal, String pace, String date) {
        this.sessionType = sessionType;
        this.distance = distance;
        this.time = time;
        this.kCal = kCal;
        this.pace = pace;
    }

    public String getDate() {
        return date;
    }

    public String getSessionType() {
        return sessionType;
    }

    public String getPace() {
        return pace;
    }

    public int getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }

    public double getkCal() {
        return kCal;
    }
}
