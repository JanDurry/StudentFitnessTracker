package android.mi.ur.studentfitnesstracker.Adapter;

import android.content.Context;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionItemAdapter extends ArrayAdapter<SessionItem> {
    private ArrayList<SessionItem> sessionList;
    private Context context;

    public SessionItemAdapter (Context context, ArrayList<SessionItem> sessionItems) {
        super(context, android.R.layout.simple_list_item_1, sessionItems);
        this.context = context;
        this.sessionList = sessionItems;
    }
}
