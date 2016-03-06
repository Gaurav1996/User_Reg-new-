package gauravagrawal.com.user_registration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;



public class after_reg extends AppCompatActivity {
    Switch myswitch;
    Intent intent2;

    private static final String MY_URL = "http://192.168.137.1:3000/db/new";

    private static final String PREFS = "AVAR";

    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_reg);
        myswitch=(Switch)findViewById(R.id.myswitch);
        myswitch.setChecked(false);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);


        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) alertdialog();
            }
        });
    }
    void alertdialog(){
        AlertDialog.Builder alertbox=new AlertDialog.Builder(after_reg.this);
        alertbox.setTitle("Confirm False Alarm");
        alertbox.setMessage("Are you sure this is a false alarm");
        alertbox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new FalseAlarm().execute();
                myswitch.setChecked(false);
            }
        });
        alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myswitch.setChecked(false);
                //Toast.makeText(getApplicationContext(),"You Clicked No",Toast.LENGTH_LONG).show();
            }
        });
        alertbox.show();
    }

    class FalseAlarm extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        RegisterUserClass ruc = new RegisterUserClass();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        //    loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ///loading.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {

            String recvresponse;

            String uuid = prefs.getString("uuid", null);

            JSONObject json = new JSONObject();
            try {
                json.put("uuid", uuid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            recvresponse = ruc.sendPostRequest(MY_URL, json);
          //  String result = ruc.sendPostRequest(MY_URL,jsonObject);
            return recvresponse;
        }

    }
}