package android.mi.ur.studentfitnesstracker.Objects;

import android.mi.ur.studentfitnesstracker.Constants.Constants;

/**
 * Created by JanDurry on 27.02.2018.
 *
 * INFO: WURDE TEILWEISE VON ÜBUNGSEINHEIT 3 "LAUFAPP" ÜBERNOMMEN
 */

public class Calculator {


    private float distance;
    private float distanceInLastTenSec;
    private long time;
    private String type;
    private int weight;

 // bei sekündlicher kCal-Berechnung nicht benötigt:   private long pause;
    private double kCalTotal;

    public void setValues(int distance, long time, int distanceInLastTenSec, String type, int weight) {
        this.type = type;
        this.distance = distance;
        this.time = time;
        this.distanceInLastTenSec = distanceInLastTenSec;
        this.weight = weight;
    }


    //berechnet Speed für 1 Std. in km/h
    private double calculateSpeed() {
        double distanceInKm = (double) distanceInLastTenSec / 1000;
        return (distanceInKm * Constants.CALCULATOR_FIVE_SECONDS_FACTOR);
    }

    //berechnet Pace in min/km
    public String calculatePace() {
        float pace = time/(distance/1000);
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
                /**MET value multiplied by weight in kilograms tells you calories burned per hour (MET*weight in kg=calories/hour)
         * source: http://www.businessinsider.de/how-to-calculate-calories-burned-exercise-met-value-2017-8?r=US&IR=T, 15.03.17
         default weight = 75kg
         z.B: 3.5 * 75kg * 1h / 3600 = 0,072916 kCal/sec
         für Laufen: 1 MET = 1 km/h;
         für Radfahren: 1 MET = 2,6 km/h;
         --> jede Sekunde currentkCal zu double kCalTotal addieren und kCalTotal als (int) returnen
         **/

        double currentkCal = 0;

        if(type.equals("Laufen")) {
            currentkCal = calculateSpeed() / Constants.CALCULATOR_FIVE_SECONDS_FACTOR * weight;
        } else {
            currentkCal = calculateSpeed() / Constants.CALCULATOR_FIVE_SECONDS_FACTOR / 2.6 * weight;
        }
        return currentkCal;
    }

}
