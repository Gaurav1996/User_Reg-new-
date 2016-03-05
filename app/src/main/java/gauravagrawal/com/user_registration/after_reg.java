package gauravagrawal.com.user_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class after_reg extends AppCompatActivity {
    Switch myswitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_reg);
        myswitch=(Switch)findViewById(R.id.myswitch);
        myswitch.setChecked(false);
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"Switch On",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Switch off",Toast.LENGTH_LONG).show();
            }
        });
    }
}
