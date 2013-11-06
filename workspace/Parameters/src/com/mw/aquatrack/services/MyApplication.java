package com.mw.aquatrack.services;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application {

	public boolean haveNetworkConnection() {
		System.out.println("internet check");	
		   boolean haveConnectedWifi = false;
			    boolean haveConnectedMobile = false;

			    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			    for (NetworkInfo ni : netInfo) {
			        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
			            if (ni.isConnected())
			                haveConnectedWifi = true;
			        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
			            if (ni.isConnected())
			                haveConnectedMobile = true;
			    }
			    return haveConnectedWifi || haveConnectedMobile;
			}



}
