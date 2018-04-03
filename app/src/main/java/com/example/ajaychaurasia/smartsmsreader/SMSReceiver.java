package com.example.ajaychaurasia.smartsmsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ajay Chaurasia on 02-Apr-18.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    /*SmsManager sms = SmsManager.getDefault();
                    if(StaticConst.SENDER_ID.equalsIgnoreCase(phoneNumber)){
                        sms.sendTextMessage(StaticConst.FORWARD_ID, null, message, null, null);
                    }*/
                }
            }
        } catch(Exception ex){
            Log.e("SMSReceiver: ","Error: "+ex.getMessage());
        }
    }
}
