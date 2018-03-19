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

// Orientierung für newDate EditText: http://findnerd.com/list/view/How-to-open-Calendar-on-clicking-the-EditText-and-set-the-date-/15238/ am 17.03.18
public class Goal extends AppCompatActivity{

    private EditText newkCalGoal;
    private TextView currentGoal;
    private SessionDatabaseAdapter sessionDB;
    private Calendar mcalendar;
    private EditText newDate;
    private int day,month,year;
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
                String newGoalString = newkCalGoal.getText().toString() + " kCal bis " + newDate.getText().toString();
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
        newkCalGoal = (EditText)findViewById(R.id.new_goal_value);
        newDate = (EditText)findViewById(R.id.new_date);
        newDate.setOnClickListener(dateClickListener);
        currentGoal = (TextView)findViewById(R.id.current_goal_value);
        /*day = mcalendar.get(Calendar.DAY_OF_MONTH);
        year = mcalendar.get(Calendar.YEAR);
        month = mcalendar.get(Calendar.MONTH);*/
    }

    View.OnClickListener dateClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
    };

    public void DateDialog() {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    newDate.setText(dayOfMonth + "." + (monthOfYear+1) + "." + year);
                }
            };
            DatePickerDialog dpDialog = new DatePickerDialog(Goal.this, listener, year, month, day);
            dpDialog.show();
    }

}