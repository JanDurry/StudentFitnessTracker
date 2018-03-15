package android.mi.ur.studentfitnesstracker.Activities;

import android.content.Intent;
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

public class PeriodicStatistics extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int WOCHENZIEL = 1000;

    private BottomNavigationView bottomNavigationView;

    private SessionDatabaseAdapter sessionDB;
    private ArrayList<SessionItem> sessions;

    private SessionItem session;

    private TextView totalMileage;
    private TextView mileageCycle;
    private TextView mileageRun;
    private TextView totalKCal;
    private TextView goalPercentage;

    private int totalMileageValue = 0;
    private int totalMileageRunValue = 0;
    private int totalMileageCycleValue = 0;
    private int totalKCalValue = 0;

    private String goalPercentageString;
    private String totalMileageString;
    private String totalMileageRunString;
    private String totalMileageCycleString;
    private String totalKCalString;



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
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    private void inflateElements() {
        setTotalValues();
        setStrings();
        mileageRun.setText(totalMileageRunString);
        mileageCycle.setText(totalMileageCycleString);
        totalMileage.setText(totalMileageString);
        totalKCal.setText(totalKCalString);
        goalPercentage.setText(goalPercentageString);
    }

    private void setStrings() {
        getTotalPercentage();
        totalMileageCycleString = String.valueOf(totalMileageCycleValue) + " km";
        totalMileageRunString = String.valueOf(totalMileageRunValue) + " km";
        totalKCalString = "Du hast ingesamt " + String.valueOf(totalKCalValue) + " kCal verbraucht!";
        totalMileageString = "Du hast insgesamt " + String.valueOf(totalMileageValue) + " km zurückgelegt!";
    }

    private void setTotalValues() {
        for (int i = 0; i < sessions.size(); i++) {
            session = sessions.get(i);
            totalMileageValue += session.getDistance();
            totalKCalValue += session.getkCal();
            if (session.getSessionType() == "Radfahren") {
                totalMileageCycleValue += session.getDistance();
            } else {
                totalMileageRunValue += session.getDistance();
            }
        }
    }

    private void getTotalPercentage() {
        double percentage = ((double) totalKCalValue / WOCHENZIEL);
        if (percentage >= 1) {
            goalPercentageString = "Du hast bereits 100% deines Wochenziels erreicht!";
        } else if (percentage < 1) {
            goalPercentageString = "Du hast bisher "+(int)(percentage * 100)+"% deines Wochenziels erreicht!";
        }
    }

    private void initElements() {
        totalMileage = (TextView) findViewById(R.id.total_mileage);
        mileageCycle = (TextView) findViewById(R.id.mileage_cycle_value);
        mileageRun = (TextView) findViewById(R.id.mileage_run_value);
        totalKCal = (TextView) findViewById(R.id.total_kCal_burnt);
        goalPercentage = (TextView) findViewById(R.id.goal_percentage);
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
