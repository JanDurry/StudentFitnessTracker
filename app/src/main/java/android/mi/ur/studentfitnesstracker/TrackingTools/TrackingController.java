package android.mi.ur.studentfitnesstracker.TrackingTools;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class TrackingController implements LocationListener {


    private static final long UPDATE_TIME = 1000;
    private static final float UPDATE_DISTANCE = 5;

    private Context context;
    private LocationUpdateListener locationUpdateListener;
    private LocationManager locationManager;

    private Location lastLocation = null;
    private Location currentLocation = null;

    public TrackingController(Context context) {
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void start() {
        startLocationRequest();
    }

    public void stop() {
        stopLocationRequest();
    }

    public void setLocationUpdateListener(LocationUpdateListener listener) {
        locationUpdateListener = listener;
    }

    public void startLocationRequest() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                UPDATE_TIME, UPDATE_DISTANCE, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                UPDATE_TIME, UPDATE_DISTANCE, this);
    }

    public void stopLocationRequest() {
        locationManager.removeUpdates(this);
    }


    /* Listener Methods

     */

    @Override
    public void onLocationChanged(Location location) {
        if (currentLocation == null) {
            currentLocation = location;
        } else {
            lastLocation = currentLocation;
            currentLocation = location;
            locationUpdateListener.onNewLocation(currentLocation, lastLocation);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface LocationUpdateListener {

        public void onNewLocation(Location current, Location last);
    }
}
