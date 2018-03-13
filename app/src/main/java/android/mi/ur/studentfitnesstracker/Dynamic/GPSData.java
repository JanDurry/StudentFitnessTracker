package android.mi.ur.studentfitnesstracker.Dynamic;

/**
 * Created by JanDurry on 07.03.2018.
 */

public class GPSData {

    public double longitude;
    public double latitude;

    private static final GPSData ourInstance = new GPSData();

    public static GPSData getInstance() {
        return ourInstance;
    }

    private GPSData() {
    }
}
