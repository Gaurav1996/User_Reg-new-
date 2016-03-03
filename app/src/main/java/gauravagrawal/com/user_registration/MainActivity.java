package gauravagrawal.com.user_registration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextuuid;
    private EditText editTextaddress;
    private EditText editTextcarmodel;
    private Button buttonRegister;

    private static final String REGISTER_URL = "http://www.automatic-report.herokuapp.com/db/new";


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
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                //String json="";
                //HashMap<String, String> data = new HashMap<String,String>();
                /*data.put("name",params[0]);
                data.put("username",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);*/
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.accumulate("name", name);
                    jsonObject.accumulate("uuid",uuid);
                    jsonObject.accumulate("address",address);
                    jsonObject.accumulate("carmodel",carmodel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = ruc.sendPostRequest(REGISTER_URL,jsonObject);
                //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,uuid,address,carmodel);
    }
}