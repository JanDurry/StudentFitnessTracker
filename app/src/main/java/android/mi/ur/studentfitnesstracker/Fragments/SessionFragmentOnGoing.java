package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Objects.Calculator;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.TrackingController;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionFragmentOnGoing extends Fragment implements TrackingController.LocationUpdateListener {

    private Button stopSession;
    private SessionFragment sessionFragment;
    private TrackingController trackingController;

    private Calculator calc;

    private TextView time;
    private TextView kCal;
    private TextView pace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.session_fragment_ongoing, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initElements();
        initButton();
        initTrackingController();

    }

    private void initElements() {
        calc = new Calculator();
        stopSession = (Button) getView().findViewById(R.id.button_stop_session);
        time = (TextView) getView().findViewById(R.id.current_time);
        kCal = (TextView) getView().findViewById(R.id.current_kCal);
        pace = (TextView) getView().findViewById(R.id.current_pace);
    }

    private void initTrackingController() {
        Activity mainMenu = getActivity();
        trackingController = new TrackingController(mainMenu);
        trackingController.setLocationUpdateListener(this);
        trackingController.start();
    }

    private void initButton() {
        sessionFragment = new SessionFragment();
        stopSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackingController.stop();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.session_fragment, sessionFragment);
                fragmentTransaction.commit();
            }
        });
    }

    /** LocationUpdateListener Callbacks
     *
     */

    @Override
    public void onNewLocation(Location current, Location last) {
        float distance = last.distanceTo(current);
        long t = current.getTime() - last.getTime();
        time.setText(String.valueOf((int) t / 1000 + " Sekunden"));
        kCal.setText(String.valueOf((double) distance / 1000 + " km"));
    }
}
