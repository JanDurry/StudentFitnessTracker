package android.mi.ur.studentfitnesstracker.Activities;

import android.app.DatePickerDialog;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Goal extends AppCompatActivity{

    private EditText newkCalGoal;
    private TextView currentGoal;
    private SessionDatabaseAdapter sessionDB;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        initDatabase();
        initElements();
        initButton();
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
    }

    private void initButton() {
        button = (Button) findViewById(R.id.button_change_goal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newGoalString = newkCalGoal.getText().toString() + " kCal (" + sessionDB.getUserGoalDate() + ")";
                currentGoal.setText(newGoalString);
                //DB funktioniert noch nicht
                /*newDate.setText(String.valueOf(sessionDB.getUserGoalDate()));

                currentGoal.setText(String.valueOf(sessionDB.getUserGoal()) + "kCal bis" + String.valueOf(sessionDB.getUserGoalDate()));

                if (!newDate.getText().equals("") && !newkCalGoal.getText().equals("") && sessionDB.checkIfUserDataExists()) {
                    sessionDB.updateUserGoal(Integer.parseInt(newkCalGoal.getText().toString()), newDate.getText().toString());
                }*/
            }
        });
    }

    private void initElements() {
        newkCalGoal = (EditText) findViewById(R.id.new_goal_value);
        currentGoal = (TextView) findViewById(R.id.current_goal_value);
    }

}