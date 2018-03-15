package android.mi.ur.studentfitnesstracker.Activities;

import android.mi.ur.studentfitnesstracker.Adapter.SessionItemAdapter;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.Objects.SessionItem;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SessionOverview extends AppCompatActivity {


    private SessionItemAdapter sessionItemAdapter;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<SessionItem> sessions;
    private SessionDatabaseAdapter sessionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_overview);
        initDatabase();
        initSessionItems();
        initListView();
    }

    private void initListView() {
        ListView sessionListView = (ListView) findViewById(R.id.list_session_overview);
        sessionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                removeSessionItemAtPosition(position);
                return true;
            }
        });
    }

    private void removeSessionItemAtPosition(int position) {
        if(sessions.get(position) != null) {
            sessionDB.removeSessionItem(sessions.get(position));
            refreshSessionList();
        }

    }

    private void refreshSessionList() {
        ArrayList tempList = sessionDB.getAllSessionItems();
        sessions.clear();
        sessions.addAll(tempList);
        sessionItemAdapter.notifyDataSetChanged();
    }

    private void initSessionItems() {
        sessions = sessionDB.getAllSessionItems();
        initSessionItemAdapter();
    }

    private void initSessionItemAdapter() {
        ListView sessionListView = (ListView) findViewById(R.id.list_session_overview);
        sessionItemAdapter = new SessionItemAdapter(this, sessions);
        sessionListView.setAdapter(sessionItemAdapter);

    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
    }

}
