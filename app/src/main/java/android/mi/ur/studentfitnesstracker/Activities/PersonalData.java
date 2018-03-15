package android.mi.ur.studentfitnesstracker.Activities;

import android.mi.ur.studentfitnesstracker.Database.SessionDatabaseAdapter;
import android.mi.ur.studentfitnesstracker.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalData extends AppCompatActivity {

    private SessionDatabaseAdapter sessionDB;
    private EditText weight;
    private Button button;

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
        weight.setText(String.valueOf(sessionDB.getUserWeight()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!weight.getText().equals("") && sessionDB.checkIfUserDataExists()) {
                    sessionDB.updateUserData(Integer.parseInt(weight.getText().toString()));
                }
            }
        });
    }

    private void initDatabase() {
        sessionDB = new SessionDatabaseAdapter(this);
        sessionDB.open();
    }
}
