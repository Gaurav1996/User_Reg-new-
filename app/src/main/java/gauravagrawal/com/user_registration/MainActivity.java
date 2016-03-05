package gauravagrawal.com.user_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextuuid;
    private EditText editTextaddress;
    private EditText editTextcarmodel;
    private Button buttonRegister;

    private static final String REGISTER_URL = "http://automatic-report.herokuapp.com/db/new";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.name);
        editTextuuid = (EditText) findViewById(R.id.uuid);
        editTextaddress = (EditText) findViewById(R.id.address);
        editTextcarmodel = (EditText) findViewById(R.id.carmodel);

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
        String name = editTextName.getText().toString().trim().toLowerCase();
        String uuid = editTextuuid.getText().toString().trim().toLowerCase();
        String address = editTextaddress.getText().toString().trim().toLowerCase();
        String carmodel = editTextcarmodel.getText().toString().trim().toLowerCase();
        register(name,uuid,address,carmodel);
    }
    private void register(final String name, final String uuid, final String address, final String carmodel) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
                }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                /*Going to second activity if successfully registered*/
                if (s.equalsIgnoreCase("Successfully registered")) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,after_reg.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("uuid", uuid);
                    jsonObject.put("address", address);
                    jsonObject.put("car", carmodel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = ruc.sendPostRequest(REGISTER_URL,jsonObject);
                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,uuid,address,carmodel);
    }
}