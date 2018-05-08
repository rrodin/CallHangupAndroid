package com.example.rinorodin.callhangup;

import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.Manifest;

public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "CallBlockReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {


        // If, the received action is not a type of "Phone_State", ignore it
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        } else {

            Log.e("1","Prvi ulaz");


                try {

                    declinePhone(context);
                    Log.e("1","Drugi ulaz");


                } catch (Exception e) {
                    e.printStackTrace();
                }

        }





    }


    public void declinePhone(Context context) throws Exception {
        try {
            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";

            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;

            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);

            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod =
                    serviceManagerNativeClass.getMethod("asInterface", IBinder.class);

            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("unable", "msg cant dissconect call....");
        }
    }




}