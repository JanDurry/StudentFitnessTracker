package android.mi.ur.studentfitnesstracker.Objects;

import android.location.Location;
import android.location.LocationManager;

/**
 * Created by JanDurry on 27.02.2018.
 */

public class LocationDistanceConverter {
    private double latitude;
    private double longitude;
    private double altitude;

    public LocationDistanceConverter(String title, double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Location getLocation() {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setAltitude(altitude);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public int getPauses() {
        return 1;
    }
}
