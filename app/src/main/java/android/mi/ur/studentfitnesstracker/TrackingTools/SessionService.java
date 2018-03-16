package android.mi.ur.studentfitnesstracker.TrackingTools;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Listener.OnSessionDataChangedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionService extends Service {

    private final IBinder iBinder = new LocalBinder();

    private OnSessionDataChangedListener onSessionDataChangedListener;

    private LocationManager locationManager;

    /* --- TIMER Variables --- */
    private boolean isrunning = false;
    private int seconds;
    private String currentTime;

    private LocationListener[] locationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    /* Binding Service */

    public class LocalBinder extends Binder {

        public SessionService getBinder() {
            return SessionService.this;
        }
    }

    /* -------- Service Methods --------- */

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

    }

    private void initializeStopWatch() {
        final Handler handler = new Handler();
        final SessionTimer timer = new SessionTimer();
        timer.startTimer();
        isrunning = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isrunning && onSessionDataChangedListener != null) {
                    onSessionDataChangedListener.onTimeUpdate(timer.toString());
                    onSessionDataChangedListener.onCurrentTimeStamp(timer.getElapsedTimeInMs());
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    /** After register listener, check if GPS_Provider is enabled **/

    public void registerListeners(OnSessionDataChangedListener listener) {
        this.onSessionDataChangedListener = listener;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            onSessionDataChangedListener.OnGpsProviderDisabled();
        }
    }



    /* -------- Service Override Methods ---------- */

    @Override
    public IBinder onBind(Intent intent)
    {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        initializeLocationManager();
        initializeStopWatch();
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, Constants.LOCATION_UPDATE_TIME, Constants.LOCATION_UPDATE_DISTANCE,
                    locationListeners[Constants.GPS_PROVIDER_LISTENER_INDEX]);
        } catch (java.lang.SecurityException ex) {
            Log.e(Constants.EXCEPTION_TAG, "SessionService onCreate " + ex.toString());
        }
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, Constants.LOCATION_UPDATE_TIME, Constants.LOCATION_UPDATE_DISTANCE,
                    locationListeners[Constants.NETWORK_PROVIDER_LISTENER_INDEX]);
        } catch (java.lang.SecurityException ex) {
            Log.e(Constants.EXCEPTION_TAG, "SessionService onCreate " + ex.toString());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (locationManager != null) {
            for (int i = 0; i < locationListeners.length; i++) {
                try {
                    locationManager.removeUpdates(locationListeners[i]);
                } catch (Exception ex) {
                    Log.e(Constants.EXCEPTION_TAG, "SessionService onDestroy " + ex.toString());
                }
            }
        }
        isrunning = false;
    }



    /* ------- Location Listener ----------- */

    private class LocationListener implements android.location.LocationListener {

        private Location startLocation = null;
        private Location current = null;
        private Location last = null;

        public LocationListener(String provider)
        {
            current = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            if (onSessionDataChangedListener != null) {
                if (startLocation == null) {
                    startLocation = location;
                    current = location;
                    onSessionDataChangedListener.onFirstLocation(startLocation);
                } else {
                    last = current;
                    current = location;
                    onSessionDataChangedListener.onNewLocation(current, last);
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

}