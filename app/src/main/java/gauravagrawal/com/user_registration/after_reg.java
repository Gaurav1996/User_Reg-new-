package gauravagrawal.com.user_registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                    alertdialog();
                }
                else
                    Toast.makeText(getApplicationContext(),"Switch off",Toast.LENGTH_LONG).show();
            }
        });
    }
    void alertdialog(){
        AlertDialog.Builder alertbox=new AlertDialog.Builder(after_reg.this);
        alertbox.setTitle("Notify Services..");
        alertbox.setMessage("You sure you need assistance for the accident?");
        alertbox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "You Clicked Yes", Toast.LENGTH_LONG).show();
            }
        });
        alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"You Clicked No",Toast.LENGTH_LONG).show();
            }
        });
        alertbox.show();
    }
}