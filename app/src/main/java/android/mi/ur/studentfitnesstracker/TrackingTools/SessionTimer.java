package android.mi.ur.studentfitnesstracker.TrackingTools;

/**
 * Created by JanDurry on 13.03.2018.
 * Es wurde sich an der Stopwatch-Klasse aus der Eieruhr-Übung orientiert
 */

public class SessionTimer {

    private long startTime;
    private long currentTime;

    private boolean running;



    public void startTimer(){
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stopTimer(){
        running = false;
    }

    public long getElapsedTimeInMs() {
        long elapsedTime = 0;
        if (running) {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        return elapsedTime;
    }

    public long getTimeMS() {
        long elapsedTime = 0;
        if (running) {
            elapsedTime = (((System.currentTimeMillis()) - startTime)/100) % 1000;
        }
        return elapsedTime;
    }

    public long getTimeSec() {
        long elapsedTime = 0;
        if (running) {
            elapsedTime = (((System.currentTimeMillis()) - startTime)/1000) % 60;
        }
        return elapsedTime;
    }

    public long getTimeMin() {
        long elapsedTime = 0;
        if (running) {
            elapsedTime = ((((System.currentTimeMillis()) - startTime)/1000) / 60) % 60;
        }
        return elapsedTime;
    }
    public long getTimeHour() {
        long elapsedTime = 0;
        if (running) {
            elapsedTime = (((((System.currentTimeMillis()) - startTime)/1000) / 60) / 60);
        }
        return elapsedTime;
    }

    public String toString() {
        return getFormattedHours() + ":" + getFormattedMins() + ":" + getFormattedSecs();
    }

    private String getFormattedHours() {
        if (getTimeHour() < 10) {
            String formattedHours = "0" + getTimeHour();
            return formattedHours;
        }
        return String.valueOf(getTimeHour());
    }

    private String getFormattedMins() {
        if (getTimeMin() < 10) {
            String formattedMins = "0" + getTimeMin();
            return formattedMins;
        }
        return String.valueOf(getTimeMin());
    }

    private String getFormattedSecs() {
        if (getTimeSec() < 10) {
            String formattedSecs = "0" + getTimeSec();
            return formattedSecs;
        }
        return String.valueOf(getTimeSec());
    }


}
