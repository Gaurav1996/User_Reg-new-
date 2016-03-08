package gauravagrawal.com.user_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextuuid;
    private EditText editTextaddress;
    private EditText editTextcarmodel;
    private EditText ICE;
    private Button buttonRegister;

    private static final String REGISTER_URL = "http://automatic-report.herokuapp.com/db/new";
    //private static final String LOCAL_URL = "http://192.168.137.1:3000/db/new";

    private static final String PREFS = "AVAR";

    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Pushbots.sharedInstance().init(this);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = prefs.edit();

        boolean isRegistered = prefs.getBoolean("isRegistered", false);

        if(isRegistered)
            startActivity(new Intent(MainActivity.this,FalseAlarmActivity.class));

        editTextName = (EditText) findViewById(R.id.name);
        editTextuuid = (EditText) findViewById(R.id.uuid);
        editTextaddress = (EditText) findViewById(R.id.address);
        editTextcarmodel = (EditText) findViewById(R.id.carmodel);
        ICE = (EditText) findViewById(R.id.ice);

        buttonRegister = (Button) findViewById(R.id.regbutton);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().toLowerCase();
        String uuid = editTextuuid.getText().toString().toLowerCase();
        String address = editTextaddress.getText().toString().toLowerCase();
        String carmodel = editTextcarmodel.getText().toString().toLowerCase();
        String ice = ICE.getText().toString().toLowerCase();
        if (name.isEmpty() || uuid.isEmpty() || address.isEmpty() || carmodel.isEmpty() || ice.isEmpty())
            Toast.makeText(getApplicationContext(), "Enter valid inputs", Toast.LENGTH_SHORT).show();
        else
            new RegisterUser(name, uuid, address, carmodel, ice).execute();
    }

    class RegisterUser extends AsyncTask<Void, Void, String>{

        ProgressDialog loading;
        RegisterUserClass ruc = new RegisterUserClass();

        String name, uuid, address, car, ice;

        public RegisterUser(String name, String uuid, String address, String car, String ice) {
            this.name = name;
            this.uuid = uuid;
            this.address = address;
            this.car = car;
            this.ice = ice;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(MainActivity.this, "Please Wait...", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            /*Going to second activity if successfully registered*/
            if (s.equalsIgnoreCase("Successfully registered")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                editor.putString("uuid", uuid);
                editor.putBoolean("isRegistered", true);
                editor.commit();

                startActivity(new Intent(MainActivity.this, FalseAlarmActivity.class));
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {

            JSONObject user = new JSONObject();
            try {
                user.put("name", name);
                user.put("uuid", uuid);
                user.put("address", address);
                user.put("car", car);
                user.put("ice", ice);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ruc.sendPostRequest(REGISTER_URL, user);
        }
    }

}