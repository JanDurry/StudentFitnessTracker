package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Listener.OnSessionDataChangedListener;
import android.mi.ur.studentfitnesstracker.Objects.Calculator;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.SessionService;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionFragmentOnGoing extends Fragment implements OnSessionDataChangedListener {

    private SessionFragment sessionFragment;
    private SessionService sessionService;
    private Calculator calc;

    private Location startLocation;
    private Location endLocation;
    private Location currentLocation;
    private Location lastLocation;

    private long timeInSecs;
    private long pausesInSecs;
    private int currentDistanceInMeters;

    private OnSessionFragmentOnGoingDataChanged onSessionFragmentOnGoingDataChanged;

    private boolean bound;

    private TextView time;
    private TextView kCal;
    private TextView pace;
    private TextView distance;
    private Button stopSession;

    private ServiceConnection sessionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.e("SERVICE CONNECTION", "connected");
            SessionService.LocalBinder binder = (SessionService.LocalBinder) service;
            sessionService = binder.getBinder();
            bound = true;
            if (sessionService != null) {
                sessionService.registerListeners(SessionFragmentOnGoing.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("SERVICE CONNECTION", "disconnected");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.session_fragment_ongoing, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initElements();
        initButton();
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        initSessionService();
        bindSessionService();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initSessionService() {
        Intent intent = new Intent(getActivity(), SessionService.class);
        getActivity().startService(intent);
        Log.e("Bound", "initSessionService");
    }

    private void stopService() {
        Intent intent = new Intent(getActivity(), SessionService.class);
        getActivity().stopService(intent);
        Log.e("Bound", String.valueOf(bound));
        onSessionFragmentOnGoingDataChanged.onFinishSession();
    }

    private void bindSessionService() {
        Intent intent = new Intent(getActivity(), SessionService.class);
        getActivity().bindService(intent, sessionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindSessionService() {
        if (bound) {
            getActivity().unbindService(sessionServiceConnection);
            bound = false;
        }
    }

    private void initElements() {
        stopSession = (Button) getView().findViewById(R.id.button_stop_session);
        time = (TextView) getView().findViewById(R.id.current_time);
        kCal = (TextView) getView().findViewById(R.id.current_kCal);
        pace = (TextView) getView().findViewById(R.id.current_pace);
        distance = (TextView) getView().findViewById(R.id.current_distance);

    }

    private void initButton() {
        sessionFragment = new SessionFragment();
        stopSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindSessionService();
                stopService();
                closeFragment();
            }
        });
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.session_fragment, sessionFragment);
        fragmentTransaction.commit();
    }

    private void calculate() {
        Calculator calc = new Calculator();
        calc.setValues(currentDistanceInMeters, timeInSecs, pausesInSecs);

        kCal.setText(calc.calculateKcal());
        pace.setText(calc.calculatePace());
        distance.setText(String.valueOf(currentDistanceInMeters) + " " + "m");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSessionFragmentOnGoingDataChanged = (OnSessionFragmentOnGoingDataChanged) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }

    /** SessionFragmentOnGoing Callbacks */

    @Override
    public void onNewLocation(Location current, Location last) {
        this.endLocation = current;
        if ((int) current.distanceTo(last) == 0) {
            pausesInSecs += ((current.getElapsedRealtimeNanos() - last.getElapsedRealtimeNanos())/ (1000*1000*1000)) % 60;
        }
        currentDistanceInMeters += current.distanceTo(last);
        onSessionFragmentOnGoingDataChanged.onDataChanged(current);
        calculate();
    }

    @Override
    public void onFirstLocation(Location first) {
        onSessionFragmentOnGoingDataChanged.onSessionStart(first);
        this.startLocation = first;
    }

    @Override
    public void onTimeUpdate(String currentTime) {
        time.setText(currentTime);
    }

    @Override
    public void onCurrentTimeStamp(long timeInMs) {
        this.timeInSecs = (timeInMs/(1000));
    }

    /* Interface to Communicate with other Fragments through Activity */

    public interface OnSessionFragmentOnGoingDataChanged {
        public void onDataChanged(Location currentLocation);
        public void onFinishSession();
        public void onSessionStart(Location startLocation);
    }
}
