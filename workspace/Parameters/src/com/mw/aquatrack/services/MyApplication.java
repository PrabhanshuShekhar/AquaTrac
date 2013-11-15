package com.mw.aquatrack.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

	// public boolean haveNetworkConnection() {
	// ConnectivityManager mConnMgr = (ConnectivityManager)
	// getSystemService(Context.CONNECTIVITY_SERVICE);
	// if (mConnMgr != null) {
	// NetworkInfo[] mNetInfo = mConnMgr.getAllNetworkInfo();
	// if (mNetInfo != null) {
	// for (int i = 0; i < mNetInfo.length; i++) {
	// if (mNetInfo[i].getState() == NetworkInfo.State.CONNECTED) {
	// return true;
	// }
	// }
	// }
	// }
	// return false;
	// }

	public static boolean hasActiveInternetConnection() {
		HttpURLConnection urlc;
		try {
			urlc = (HttpURLConnection) (new URL("https://www.google.co.in/")
					.openConnection());// http://www.google.com
			urlc.setRequestProperty("User-Agent", "Test");
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(1500);
			urlc.connect();
			return (urlc.getResponseCode() == 200);
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			System.out.println("except");
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("except");
		}

		return false;
	}

	Integer i;

	public int combinedInternetTest() {
		/*
		 * We can't return AlertDialog.Builder from this function because it
		 * requires context as argument & we do not have context available in
		 * this class
		 */
		if (!haveNetworkConnection()) {
			System.out.println("network problem");
			return 1;
		} else {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					if (!hasActiveInternetConnection())
						i = Integer.valueOf(5);
				}
			});
			t.start();

			while (!(t.getState() == Thread.State.TERMINATED)) {
				try {
					Thread.currentThread();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (i != null) {
				System.out.println("internet problem");
				return 2;
			}
		}
		return 0;
	}
}