package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Activities.MainMenu;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.Listener.OnSessionDataChangedListener;
import android.mi.ur.studentfitnesstracker.Objects.Calculator;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.SessionService;
import android.os.Bundle;
import android.os.IBinder;
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

    private SessionDatabaseAdapter sessionDB;

    private Location startLocation;
    private Location endLocation;
    private Location currentLocation;
    private Location lastLocation;

    private long timeInSecs;
    private int currentDistanceInMeters;
    private int distanceInLastTenSec;
    private double kCalTotal;
    private String currentPace;
    private String currentTime;
    private Calculator calc;

    private OnSessionFragmentOnGoingDataChanged onSessionFragmentOnGoingDataChanged;

    private boolean bound;

    private TextView time;
    private TextView kCal;
    private TextView pace;
    private TextView distance;
    private Button stopSession;
    private TextView currentSessionType;
    private String sessionType;


    /* set bound when service connected */

    private ServiceConnection sessionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            SessionService.LocalBinder binder = (SessionService.LocalBinder) service;
            sessionService = binder.getBinder();
            bound = true;
            if (sessionService != null) {
                sessionService.registerListeners(SessionFragmentOnGoing.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    /** Fragment Override Methods */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.session_fragment_ongoing, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initElements();
        initButton();
        initDatabase();
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(getActivity());
        sessionDB.open();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSessionFragmentOnGoingDataChanged = (OnSessionFragmentOnGoingDataChanged) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /* ------ Service Methods ---- */

    private void initSessionService() {
        Intent intent = new Intent(getActivity(), SessionService.class);
        getActivity().startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(getActivity(), SessionService.class);
        getActivity().stopService(intent);
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

    /* ------ Init Elements and public Method to set sessiontype ------ */

    private void initElements() {
        Bundle arguments = getArguments();
        stopSession = (Button) getView().findViewById(R.id.button_stop_session);
        time = (TextView) getView().findViewById(R.id.current_time);
        kCal = (TextView) getView().findViewById(R.id.current_kCal);
        pace = (TextView) getView().findViewById(R.id.current_pace);
        distance = (TextView) getView().findViewById(R.id.current_distance);
        currentSessionType = (TextView) getView().findViewById(R.id.current_session_type);
        currentSessionType.setText(arguments.getString("SESSION_TYPE"));
    }

    private void initButton() {
        sessionFragment = new SessionFragment();
        stopSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainMenu)getActivity()).enableNavigationBar();
                unbindSessionService();
                stopService();
                closeFragment();
            }
        });
    }

    /* close Fragment */

    private void closeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.session_fragment, sessionFragment);
        fragmentTransaction.commit();
        onSessionFragmentOnGoingDataChanged.onFinishSession(currentSessionType.getText().toString(), currentDistanceInMeters, currentTime, kCalTotal, currentPace);
    }

    /* ---- calculate ---- */

    private void calculate() {
        Calculator calc = new Calculator();
        String currentDistanceInMetersString = String.valueOf(currentDistanceInMeters) + " m";
        calc.setValues(currentDistanceInMeters, timeInSecs, distanceInLastTenSec, currentSessionType.getText().toString(), sessionDB.getUserWeight());
        kCalTotal +=  calc.calculateKcal();
        currentPace = calc.calculatePace();
        kCal.setText(String.valueOf((int)kCalTotal));
        pace.setText(currentPace);
        distance.setText(currentDistanceInMetersString);
        checkIfSessionAimAccomplished();
    }

    /** send Notfication if session aim has been accomplished */

    private void checkIfSessionAimAccomplished() {
        int sessionAim = sessionDB.getUserSessionAim();
        Bundle arguments = getArguments();
        String sessionType = arguments.getString("SESSION_TYPE");
        if (sessionType.equals(Constants.SESSION_TYPE_CYCLE)) {
            //.setSmallIcon(R.drawable.img_cycle)
        } else if (sessionType.equals(Constants.SESSION_TYPE_RUN)){
            //.setSmallIcon(R.drawable.img_cycle)
        }


    }

    /** SessionFragmentOnGoing Callbacks */

    @Override
    public void onNewLocation(Location current, Location last) {
        this.endLocation = current;
        //Wert wurde durch 2 geteilt, da die Schätzung der Methode distanceTo doppelt so hoch lag. Durch Testen wurde sich auf /2 geeinigt.
        currentDistanceInMeters += current.distanceTo(last)/Constants.APPROXIMATION_SMOOTHER;
        distanceInLastTenSec = (int) current.distanceTo(last)/Constants.APPROXIMATION_SMOOTHER;
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
        this.currentTime = currentTime;
        time.setText(currentTime);
    }

    @Override
    public void OnGpsProviderDisabled() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.gps_permission_message)
                .setCancelable(false)
                .setPositiveButton(R.string.gps_permission_settings, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.gps_permission_deny, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCurrentTimeStamp(long timeInMs) {
        this.timeInSecs = (timeInMs/(Constants.MS_TO_SECONDS));
    }

    /* Interface to Communicate with other Fragments through Activity */

    public interface OnSessionFragmentOnGoingDataChanged {
        public void onDataChanged(Location currentLocation);
        public void onFinishSession(String sessionType, int distance, String time, double kCal, String pace);
        public void onSessionStart(Location startLocation);
    }
}
