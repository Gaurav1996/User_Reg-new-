package gauravagrawal.com.user_registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class FalseAlarmActivity extends AppCompatActivity {
    Switch falseAlarmSwitch;

    private static final String SERVER_URL = "http://automatic-report.herokuapp.com/db/cancel/";
    //private static final String LOCAL_URL = "http://192.168.137.1:3000/db/cancel";
    private static final String PREFS = "AVAR";

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false_alarm);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        falseAlarmSwitch = (Switch) findViewById(R.id.myswitch);
        falseAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) alertDialog();
            }
        });
    }

    public void alertDialog(){

        new AlertDialog.Builder(FalseAlarmActivity.this)
                .setTitle("Confirm False Alarm")
                .setMessage("Are you sure this is a false alarm?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new FalseAlarm().execute();
                        falseAlarmSwitch.setChecked(false);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        falseAlarmSwitch.setChecked(false);
                    }
                })
                .show();
    }

    class FalseAlarm extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {

            String response;
            String uuid = prefs.getString("uuid", null);

            FalseAlarmRequest fref = new FalseAlarmRequest();
            response = fref.createGetRequest(SERVER_URL + uuid);
            return response;
        }
    }
}