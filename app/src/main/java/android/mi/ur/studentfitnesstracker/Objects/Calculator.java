package android.mi.ur.studentfitnesstracker.Objects;

import android.mi.ur.studentfitnesstracker.Constants.Constants;

/**
 * Created by JanDurry on 27.02.2018.
 */

public class Calculator {


    private float distance;
    private float distanceInLastTenSec;
    private long time;
    private int weight;
    private double kCalTotal;

    private String type;


    public void setValues(int distance, long time, int distanceInLastTenSec, String type, int weight) {
        this.type = type;
        this.distance = distance;
        this.time = time;
        this.distanceInLastTenSec = distanceInLastTenSec;
        this.weight = weight;
    }


    //berechnet Speed für 1 Std. in km/h
    private double calculateSpeed() {
        double distanceInKm = (double) distanceInLastTenSec / Constants.METERS_TO_KILOMETERS;
        return (distanceInKm * Constants.CALCULATOR_FIVE_SECONDS_FACTOR);
    }

    //berechnet Pace in min/km
    public String calculatePace() {
        float pace = time/(distance/Constants.METERS_TO_KILOMETERS);
        long secs = (long)pace%60;
        long mins = (long)(pace/60)%60;
        return "" + getFormattedTime(mins) + ":" + getFormattedTime(secs) + " min/km";

    }

    //Darstellung in 01:23 statt 83.0000
    private String getFormattedTime(long num) {
        if(num < 10) {
            String formattedNum = "0" + num;
            return formattedNum;
        }
        return String.valueOf(num);
    }

    //berechnet verbrauchte kCal in der letzten Sekunde
    public double calculateKcal() {
        double currentkCal = 0;

        if(type.equals(Constants.SESSION_TYPE_RUN)) {
            currentkCal = calculateSpeed() / Constants.CALCULATOR_FIVE_SECONDS_FACTOR * weight;
        } else {
            currentkCal = calculateSpeed() / Constants.CALCULATOR_FIVE_SECONDS_FACTOR / Constants.CYCLE_KCAL_FACTOR * weight;
        }
        return currentkCal;
    }

}
