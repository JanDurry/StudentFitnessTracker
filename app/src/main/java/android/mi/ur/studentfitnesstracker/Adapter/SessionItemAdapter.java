package android.mi.ur.studentfitnesstracker.Adapter;

import android.content.Context;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.mi.ur.studentfitnesstracker.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionItemAdapter extends ArrayAdapter<SessionItem> {
    private ArrayList<SessionItem> sessionList;
    private Context context;

    public SessionItemAdapter (Context context, ArrayList<SessionItem> sessionItems) {
        super(context, R.layout.session_list_item, sessionItems);
        this.context = context;
        this.sessionList = sessionItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.session_list_item, null);

        }

        SessionItem sessionItem = sessionList.get(position);

        if (sessionItem != null) {
            ImageView sessionImg = (ImageView) v.findViewById(R.id.session_item_img);
            TextView sessionType = (TextView) v.findViewById(R.id.session_item_type);
            TextView sessionDate = (TextView) v.findViewById(R.id.session_item_date);
            TextView sessionTime = (TextView) v.findViewById(R.id.session_item_time);
            TextView sessionDistance = (TextView) v.findViewById(R.id.session_item_distance);
            TextView sessionPace = (TextView) v.findViewById(R.id.session_item_pace);
            TextView sessionKCal = (TextView) v.findViewById(R.id.session_item_kCal);

            int distance = sessionItem.getDistance();


            String kCal = String.valueOf((int) sessionItem.getkCal() + " kCal");
            
            sessionDate.setText(sessionItem.getDate());
            sessionTime.setText(sessionItem.getTime() + " min");
            sessionDistance.setText(formatDistance(distance));
            sessionPace.setText(sessionItem.getPace());
            sessionKCal.setText(kCal);

            String isCycle = "Radfahren";

            if(sessionItem.getSessionType().equals(isCycle)) {
                sessionImg.setImageResource(R.drawable.img_cycle);
            } else
            sessionImg.setImageResource(R.drawable.img_run);
        }

        return v;
    }

    private String formatDistance(int distance) {
        if(distance > 1000) {
            int distanceKMBeforeComma = (distance - (distance%1000)) / 1000;
            int distanceKMAfterComma = (distance%1000) / 10;
             String distanceKMString = "" + distanceKMBeforeComma + "," + distanceKMAfterComma;
             return  distanceKMString + " km";
        } else {
            return String.valueOf(distance) + " m";
        }
    }


}
