package android.mi.ur.studentfitnesstracker.Objects;

/**
 * Created by JanDurry on 27.02.2018.
 *
 * INFO: WURDE VON ÜBUNGSEINHEIT 3 "LAUFAPP" ÜBERNOMMEN
 */

public class Calculator {

    private static final int FAST_JOGGING_KCAL_PER_HOUR = 840;
    private static final int SLOW_JOGGING_KCAL_PER_HOUR = 530;
    private static final double MAX_SLOW_KM_PER_HOUR = 7;
    private final static double MAX_SLOW_KM_H = 7;

    private float distance;
    private long time;
    private long pause;

    public void setValues(int distance, long time, long pause) {
        this.distance = distance;
        this.time = time;
        this.pause = pause;
    }

    private double calculateSpeed() {

        return ((double) distance/1000) / (((double) time/60 - (double) pause/60));
    }

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


    public String calculateKcal() {
        double speed = calculateSpeed();
        double kcal = 0;
        if (speed > MAX_SLOW_KM_H) {
            kcal = FAST_JOGGING_KCAL_PER_HOUR * (((double) time / 60)/60);
        } else {
            kcal = SLOW_JOGGING_KCAL_PER_HOUR * (((double) time / 60)/60);
        }
        return "" + (int) kcal + " kCal";
    }

}
