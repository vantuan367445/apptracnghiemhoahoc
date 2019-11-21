package com.example.tuanvatvo.demo2.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.demo2.R;
import com.example.tuanvatvo.demo2.activity.UiActivity;

public class CheckConnectiom {

    public static boolean checkCon(Context context){
        boolean haveConnectedWifi  = false;
        boolean haveConnectedMobile  = false;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for(NetworkInfo info : networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    haveConnectedWifi = true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    haveConnectedMobile = true;
        }


        return haveConnectedMobile || haveConnectedWifi;
    }

    public static  void  toastCheckConnection(Context context){
        Toast.makeText(context, "Không có internet", Toast.LENGTH_SHORT).show();
    }

}
