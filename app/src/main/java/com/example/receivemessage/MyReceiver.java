package com.example.receivemessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";

    public static String PHONENUM ="com.exampe.nextpage.PHONENUM";
    public static String MSG  ="com.example.nextpage.MSG";


    @Override
    public void onReceive(Context context, Intent intent) {


        //retrives the general action to be performed and display onlog
        Log.i(TAG, "Intent Received: " + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED) {
            //retrieves a map of extended data from the intent
            Bundle dataBunddle = intent.getExtras();
            if (dataBunddle != null) {
                //creating PDU(Protocol Data Unit) Obj which is a protocol for transfer message
                Object[] mypdu = (Object[]) dataBunddle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i < mypdu.length; i++) {
                    //for build versions >= API Level 23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = dataBunddle.getString("format");
                        //form PDU we get all Obj and SmsMessage Obj using following line of code
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    } else {
                        //<API Level 23
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg= message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                    MSG = message[i].getMessageBody();
                    PHONENUM = message[i].getOriginatingAddress();
                }
                Toast.makeText(context,"Message: "+msg+"\nNumber: "+phoneNo,Toast.LENGTH_LONG).show();
                Toast.makeText(context,"Message: "+MSG+"\nNumber: "+PHONENUM,Toast.LENGTH_LONG).show();
            }
        }
    }
}