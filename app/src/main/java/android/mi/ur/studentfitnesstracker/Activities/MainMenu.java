package android.mi.ur.studentfitnesstracker.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Adapter.SessionItemAdapter;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.Fragments.SessionFragment;
import android.mi.ur.studentfitnesstracker.Fragments.SessionFragmentOnGoing;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.mi.ur.studentfitnesstracker.Fragments.MapFragment;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements SessionFragmentOnGoing.OnSessionFragmentOnGoingDataChanged {

    private SessionFragment sessionFragment;
    private MapFragment map;

    private ArrayList<SessionItem> sessions;
    private SessionItemAdapter sessionsAdapter;
    private SessionDatabaseAdapter sessionDB;

    /** Überschriebene Methoden **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFragmentLayouts();
        initInitialFragment();
        initDatabase();
    }

    private void initFragmentLayouts() {
        sessionFragment = new SessionFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Eigene Methoden **/

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
        sessions = sessionDB.getAllSessionItems();
    }

    private void initInitialFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.session_fragment, sessionFragment);
        fragmentTransaction.commit();
    }

    /* Callbacks aus dem SessionFragmentOnGoing
    *  ruft öffentliche Methoden des MapFragment auf.
    * */

    @Override
    public void onDataChanged(Location currentLocation) {
        Log.e("MAP", "onDataChanged");
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.locationChange(currentLocation);
    }

    @Override
    public void onFinishSession() {
        Log.e("MAP", "onFinishSession");
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.onFinishSession();
    }

    @Override
    public void onSessionStart(Location startLocation) {
        Log.e("MAP", "onSessionStart");
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.onStartLocation(startLocation);
    }
}
