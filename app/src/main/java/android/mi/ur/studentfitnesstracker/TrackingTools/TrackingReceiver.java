package android.mi.ur.studentfitnesstracker.TrackingTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by JanDurry on 07.03.2018.
 */

public class TrackingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Double testLongitude = intent.getDoubleExtra("Longitude",0);
        Log.e("TESTGPS", "Receiver " + testLongitude);
    }
}
