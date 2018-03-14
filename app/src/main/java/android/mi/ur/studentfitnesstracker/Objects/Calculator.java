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
    private float time;
    private int pause;

    public void setValues(int distance, int time, int pause) {
        this.distance = distance;
        this.time = time;
        this.pause = pause;
    }

    private double calculateSpeed() {
        return ((double) distance) / (((double) time - (double) pause) / 60);
    }

    public double calculatePace() {
        return time/(double) distance;
    }


    public double calculateKcal() {
        double speed = calculateSpeed();
        double kcal = 0;
        if (speed > MAX_SLOW_KM_H) {
            kcal = FAST_JOGGING_KCAL_PER_HOUR * ((double) time / 60);
        } else {
            kcal = SLOW_JOGGING_KCAL_PER_HOUR * ((double) time / 60);
        }
        return kcal;
    }

}
