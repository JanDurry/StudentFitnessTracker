package android.mi.ur.studentfitnesstracker.Activities;

import android.content.Intent;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class PeriodicStatistics extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    private SessionDatabaseAdapter sessionDB;
    private ArrayList<SessionItem> sessions;

    private SessionItem session;
    private TextView totalMileage;
    private TextView mileageCycle;
    private TextView mileageRun;
    private TextView mileageAvgRun;
    private TextView mileageAvgCycle;

    private TextView totalKCal;
    private TextView goalPercentage;


    private int totalMileageValue = 0;
    private int totalMileageRunValue = 0;
    private int totalMileageCycleValue = 0;
    private int totalKCalValue = 0;

    private double avgRun;
    private double avgCycle;

    private String totalMileageString;
    private String totalMileageRunString;
    private String totalMileageCycleString;
    private String totalKCalString;
    private String avgRunString;
    private String avgCycleString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_statistic);
        initDatabase();
        initNavgationView();
        initSessionArrayList();
        initElements();
        inflateElements();
    }

    private void initNavgationView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(Constants.BOTTOM_NAVIGATION_VIEW_PERIODIC_STATISTICS_ID).setChecked(true);
    }

    private void inflateElements() {
        setTotalValues();
        getAverageValues();
        setStrings();
        mileageRun.setText(totalMileageRunString);
        mileageCycle.setText(totalMileageCycleString);
        totalMileage.setText(totalMileageString);
        totalKCal.setText(totalKCalString);
        mileageAvgRun.setText(avgRunString);
        mileageAvgCycle.setText(avgCycleString);
    }

    private void setStrings() {
        totalMileageCycleString = String.valueOf(totalMileageCycleValue) + " m";
        totalMileageRunString = String.valueOf(totalMileageRunValue) + " m";
        avgRunString = "Ø " + String.valueOf((int)avgRun) + " m";
        avgCycleString = "Ø " + String.valueOf((int)avgCycle) + " m";
        totalKCalString = "Du hast ingesamt " + String.valueOf(totalKCalValue) + " kCal verbraucht!";
        totalMileageString = "Du hast insgesamt " + String.valueOf(totalMileageValue) + " m zurückgelegt!";
    }

    private void getAverageValues() {
        int[] totals = getTotalCycleSessions();
        avgRun = (double) totalMileageRunValue/totals[Constants.TOTALS_RUN_INDEX];
        avgCycle = (double) totalMileageCycleValue/totals[Constants.TOTALS_CYCLE_INDEX];
    }

    private int[] getTotalCycleSessions() {
        int totalRun = 0;
        int totalCycle = 0;
        int [] totals = new int[2];
        for (SessionItem session : sessions) {
            if (session.getSessionType().equals(Constants.SESSION_TYPE_RUN)) {
                totalRun += 1;
            } else {
                totalCycle += 1;
            }
        }
        totals[Constants.TOTALS_RUN_INDEX] = totalRun;
        totals[Constants.TOTALS_CYCLE_INDEX] = totalCycle;
        return totals;
    }

    private void setTotalValues() {
        for (int i = 0; i < sessions.size(); i++) {
            session = sessions.get(i);
            totalMileageValue += session.getDistance();
            totalKCalValue += session.getkCal();
            if (session.getSessionType().equals(Constants.SESSION_TYPE_CYCLE)) {
                totalMileageCycleValue += session.getDistance();
            } else {
                totalMileageRunValue += session.getDistance();
            }
        }
    }

    private void initElements() {
        totalMileage = (TextView) findViewById(R.id.total_mileage);
        mileageCycle = (TextView) findViewById(R.id.mileage_cycle_value);
        mileageRun = (TextView) findViewById(R.id.mileage_run_value);
        totalKCal = (TextView) findViewById(R.id.total_kCal_burnt);
        goalPercentage = (TextView) findViewById(R.id.goal_percentage);
        mileageAvgCycle = (TextView) findViewById(R.id.mileage_cycle_avg_value);
        mileageAvgRun = (TextView) findViewById(R.id.mileage_run_avg_value);
    }



    private void initSessionArrayList() {
        sessions = sessionDB.getAllSessionItems();
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.new_session) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
        if (itemId == R.id.sessions) {
            Intent intent = new Intent(this, SessionOverview.class);
            startActivity(intent);
        }
        return true;
    }
}
