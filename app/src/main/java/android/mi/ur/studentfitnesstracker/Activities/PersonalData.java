package android.mi.ur.studentfitnesstracker.Activities;

import android.content.Intent;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalData extends AppCompatActivity {

    private SessionDatabaseAdapter sessionDB;
    private EditText weight;
    private EditText goalValue;
    private Button button;
    private EditText newkCalGoal;
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
        goalValue = (EditText) findViewById(R.id.new_goal_value);
        goalValue.setText("");
        weight.setText(String.valueOf(sessionDB.getUserWeight()));
        newkCalGoal = (EditText) findViewById(R.id.new_goal_value);
        currentGoal = (TextView) findViewById(R.id.current_goal_value);
        currentGoal.setText(String.valueOf(sessionDB.getUserGoal()) + "kcal (" + sessionDB.getUserGoalDate() + ")");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!weight.getText().equals("") && !goalValue.getText().equals("") && sessionDB.checkIfUserDataExists()) {
                    sessionDB.updateUserData(Integer.parseInt(weight.getText().toString()), Integer.parseInt(goalValue.getText().toString()));
                    String newGoalString = newkCalGoal.getText().toString() + " kCal (" + sessionDB.getNewUserGoalDate() + ")";
                    currentGoal.setText(newGoalString);
                }
            }
        });
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();

    }
}
