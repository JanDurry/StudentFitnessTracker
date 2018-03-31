package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.mi.ur.studentfitnesstracker.Activities.MainMenu;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.SessionService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionFragment extends Fragment {

    private SessionService sessionService;

    private Switch sessionType;
    private SessionFragmentOnGoing sessionFragmentOnGoing;

    private Button startSessionButton;

    private boolean backEnabled;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.session_fragment, parent, false);
    }

    private void initElements() {
        sessionType = (Switch) getView().findViewById(R.id.switch_run_cycle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initElements();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButton();
    }


    private void initButton() {
        sessionFragmentOnGoing = new SessionFragmentOnGoing();
        startSessionButton = (Button) getView().findViewById(R.id.button_start_session);
        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainMenu)getActivity()).disableNavigationBar();
                ((MainMenu)getActivity()).disableToolBar();
                ((MainMenu)getActivity()).disableBackButton();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle type = new Bundle();
                type.putString( "SESSION_TYPE" , getSessionType());
                sessionFragmentOnGoing.setArguments(type);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.session_fragment, sessionFragmentOnGoing);
                fragmentTransaction.commit();
            }
        });
    }

    private String getSessionType() {
        if (sessionType.isChecked()) {
            return Constants.SESSION_TYPE_CYCLE;
        }
        return Constants.SESSION_TYPE_RUN;
    }

}
