package android.mi.ur.studentfitnesstracker.Listener;

import android.location.Location;

/**
 * Created by JanDurry on 24.02.2018.
 */

public interface OnSessionDataChangedListener {

    public void onNewLocation(Location current, Location last);
    public void onFirstLocation(Location first);
    public void onTimeUpdate(String currentTimeString);
    public void onCurrentTimeStamp(long timeInMs);

}
