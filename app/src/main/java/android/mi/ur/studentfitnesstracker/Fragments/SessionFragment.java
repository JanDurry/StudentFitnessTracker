package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.TrackingController;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class SessionFragment extends Fragment {

    private SessionFragmentOnGoing sessionFragmentOnGoing;
    private TrackingController trackingController;

    /* Fragment Objects
     */

    private Button startSessionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.session_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.session_fragment, sessionFragmentOnGoing);
                fragmentTransaction.commit();
            }
        });
    }


}
