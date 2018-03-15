package android.mi.ur.studentfitnesstracker.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Adapter.SessionItemAdapter;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.Fragments.MapFragment;
import android.mi.ur.studentfitnesstracker.Fragments.SessionFragment;
import android.mi.ur.studentfitnesstracker.Fragments.SessionFragmentOnGoing;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainMenu extends AppCompatActivity implements SessionFragmentOnGoing.OnSessionFragmentOnGoingDataChanged,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private SessionFragment sessionFragment;
    private MapFragment map;
    private SessionFragmentOnGoing sessionFragmentOnGoing;
    private BottomNavigationView bottomNavigationItemView;

    private SessionItem sessionItem;
    private ArrayList<SessionItem> sessions;
    private SessionItemAdapter sessionsAdapter;
    private SessionDatabaseAdapter sessionDB;

    /** Überschriebene Methoden **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        checkLocationPermission();
        setSupportActionBar(toolbar);
        initFragmentLayouts();
        initInitialFragment();
        initDatabase();
    }

    /** public Method to disable BottomNavigationItems during session */

    public void disableNavigationBar() {
        Menu menu = bottomNavigationItemView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(false);
        }
    }

    public void enableNavigationBar() {
        Menu menu = bottomNavigationItemView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(true);
        }
    }

    /* Check AppPermissions -> siehe https://developer.android.com/training/permissions/requesting.html
    * https://stackoverflow.com/questions/40142331/how-to-request-location-permission-at-runtime-on-android-6 */

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_permission_title)
                        .setMessage(R.string.app_permission_message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainMenu.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        Constants.PERMISSION_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSION_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, R.string.app_permission_enabled, Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this, R.string.app_permission_disabled, Toast.LENGTH_LONG).show();

                }
                return;
            }

        }
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
        if (id == R.id.personal_data) {
            Intent intent = new Intent(this, PersonalData.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** andere OnCreate Methoden **/

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
        if (!sessionDB.checkIfUserDataExists()) {
            sessionDB.insertUserData(Constants.DEFAULT_WEIGHT);
        }
        sessions = sessionDB.getAllSessionItems();
    }

    private void initInitialFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.session_fragment, sessionFragment);
        fragmentTransaction.commit();
    }

    private void initFragmentLayouts() {
        sessionFragment = new SessionFragment();
        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(this);
        bottomNavigationItemView.getMenu().getItem(1).setChecked(true);
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
    public void onFinishSession(String sessionType, int distance, String time, double kCal, String pace) {
        Log.e("MAP", "onFinishSession");
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.onFinishSession();
        if (kCal > 20) { // If the user burned less than 20 kCal no session item will be created
            Date date = Calendar.getInstance(TimeZone.getTimeZone("CET")).getTime();
            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
            String formattedDate = formatDate.format(date);
            sessionItem = new SessionItem(sessionType, distance, time, kCal, pace, formattedDate);
            sessionDB.insertSessionItem(sessionItem);
            Toast.makeText(this, "Glückwunsch! Du hast " + (int)kCal + " kCal verbraucht!", Toast.LENGTH_LONG).show();
            // save in Database
        } else {
            Toast.makeText(this, "Es wurde keine Session hinzugefügt, weil dein Kalorienverbrauch zu gering war!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSessionStart(Location startLocation) {
        Log.e("MAP", "onSessionStart");
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        map.onStartLocation(startLocation);
    }

    /* BottomNavigationView

     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        SessionFragmentOnGoing sessionFragmentOnGoing = (SessionFragmentOnGoing) getFragmentManager().findFragmentById(R.id.session_fragment_onGoing);
        int itemId = item.getItemId();
        if (itemId == R.id.progress) {
            Intent intent = new Intent(this, PeriodicStatistics.class);
            startActivity(intent);
        }
        if (itemId == R.id.sessions) {
            Intent intent = new Intent(this, SessionOverview.class);
            startActivity(intent);
        }
        return true;
    }
}
