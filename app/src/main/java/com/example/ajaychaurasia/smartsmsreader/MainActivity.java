package com.example.ajaychaurasia.smartsmsreader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    SMSReceiver smsReceiver;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SmsManager smsManager = SmsManager.getDefault();

        (findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsReceiver = new SMSReceiver();
                /*IntentFilter intentFilter = new IntentFilter("Intent.action.SMS_RECEIVED_ACTION");
                registerReceiver(new SMSReceiver(), intentFilter);*/
                StaticConst.SENDER_ID = ((EditText) (findViewById(R.id.sms_sender_id))).getText().toString();
                StaticConst.FORWARD_ID = ((EditText) (findViewById(R.id.sms_forward_id))).getText().toString();

                /*IntentFilter filter = new IntentFilter();
                filter.addAction("android.provider.Telephony.SMS_RECEIVED_ACTION");
                registerReceiver(smsReceiver, filter);*/
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null!=smsReceiver){
            unregisterReceiver(smsReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        permissionGrant();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void permissionGrant() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                showExplanation("Permission Needed", "Permission is required to read and send SMS",
                        Manifest.permission.READ_PHONE_STATE, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                requestPermission(Manifest.permission.READ_SMS, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            //Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }
}
