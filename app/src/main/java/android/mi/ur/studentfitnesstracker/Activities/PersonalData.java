package android.mi.ur.studentfitnesstracker.Activities;

import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PersonalData extends AppCompatActivity {

    private SessionDatabaseAdapter sessionDB;
    private EditText weight;
    private EditText newGoalValue;
    private Button button;

    private TextView currentGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        initDatabase();
        initButton();
    }

    private void initButton() {
        button = (Button) findViewById(R.id.button_change_personal_data);
        weight = (EditText) findViewById(R.id.weight_value);
        newGoalValue = (EditText) findViewById(R.id.new_goal_value);
        newGoalValue.setText("");
        weight.setText(String.valueOf(sessionDB.getUserWeight()));
        currentGoal = (TextView) findViewById(R.id.current_goal_value);
        currentGoal.setText(String.valueOf(sessionDB.getUserGoal()) + "kcal (" + formatUserGoalDate() + ")");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!weight.getText().equals("") && !newGoalValue.getText().equals("") && sessionDB.checkIfUserDataExists()) {
                    Date date = Calendar.getInstance(TimeZone.getTimeZone("CET")).getTime();
                    SimpleDateFormat formatDate = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
                    String formattedDate = formatDate.format(date);
                    sessionDB.updateUserData(Integer.parseInt(weight.getText().toString()), Integer.parseInt(newGoalValue.getText().toString()));
                    currentGoal.setText(newGoalValue.getText().toString() + "kcal (" + formattedDate + ")");
                }
            }
        });
    }
    //für diese Methode wurde sich an folgender Quelle orientiert: https://stackoverflow.com/questions/9945072/convert-string-to-date-in-java (am 29.03.18)
    private String formatUserGoalDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(sessionDB.getUserGoalDate());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat formatDate = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        return  formatDate.format(convertedDate);
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();

    }
}
