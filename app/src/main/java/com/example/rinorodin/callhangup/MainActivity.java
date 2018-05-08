package com.example.rinorodin.callhangup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.telephony.TelephonyManager;
import android.widget.Button;
import 	java.lang.reflect.Method;
import java.lang.Object;
import android.content.IntentFilter;
import android.app.Activity;
import android.widget.Toast;
import android.content.ComponentName;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;
    Button hangup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hangup_button = (Button) findViewById(R.id.hangup_b);




        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
        }

        hangup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneStateReceiver reciever= new PhoneStateReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.intent.action.PHONE_STATE");
                registerReceiver(reciever,filter);

                try {

                    reciever.declinePhone(getBaseContext());
                    Toast.makeText(getApplicationContext(), "Call disconnected", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                unregisterReceiver(reciever);


            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission NOT granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }
    }

}