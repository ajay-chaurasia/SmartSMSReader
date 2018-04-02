package com.example.ajaychaurasia.smartsmsreader;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmsManager smsManager = SmsManager.getDefault();

        (findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*IntentFilter intentFilter = new IntentFilter("Intent.action.SMS_RECEIVED_ACTION");
                registerReceiver(new SMSReceiver(), intentFilter);*/
                StaticConst.SENDER_ID = ((EditText)(findViewById(R.id.sms_sender_id))).getText().toString();
                StaticConst.FORWARD_ID = ((EditText)(findViewById(R.id.sms_forward_id))).getText().toString();

                IntentFilter filter = new IntentFilter();
                filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED /*"SMS_RECEIVED_ACTION"*/);
                getApplicationContext().registerReceiver(new SMSReceiver(), filter);
            }
        });


    }
}
