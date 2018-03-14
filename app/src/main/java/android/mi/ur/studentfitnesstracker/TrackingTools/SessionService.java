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

    private LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    /** Test **/
    private static final String TAG = "TESTGPS";

    /* Binding Service */

    public class LocalBinder extends Binder {

        public SessionService getBinder() {
            return SessionService.this;
        }
    }

    /* -------- Service Methods --------- */

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void initializeStopWatch() {
        Log.e(TAG, "initializeStopWatch");
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

    public void registerListeners(OnSessionDataChangedListener listener) {
            Log.e(TAG, "registerListeners");
            this.onSessionDataChangedListener = listener;
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
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        initializeLocationManager();
        initializeStopWatch();
        Log.e(TAG, "onCreate");
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, Constants.LOCATION_UPDATE_TIME, Constants.LOCATION_UPDATE_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, Constants.LOCATION_UPDATE_TIME, Constants.LOCATION_UPDATE_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    locationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
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
            Log.e(TAG, "LocationListener " + provider);
            current = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            if (onSessionDataChangedListener != null) {
                if (startLocation == null) {
                    startLocation = location;
                    current = location;
                    onSessionDataChangedListener.onFirstLocation(startLocation);
                }
                last = current;
                current = location;
                onSessionDataChangedListener.onNewLocation(current, last);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle bundle) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }
    }

}