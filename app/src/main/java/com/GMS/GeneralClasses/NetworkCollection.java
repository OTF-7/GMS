package com.GMS.GeneralClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkCollection {

    private static ConnectivityManager sConnectivityManager;
    private static NetworkInfo sNetworkInfo;

    public NetworkCollection() {

    }

    public static boolean checkConnection(Context context) {
        sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        sNetworkInfo = sConnectivityManager.getActiveNetworkInfo();
        if (sNetworkInfo != null) {
            switch (sNetworkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return true;
                case ConnectivityManager.TYPE_MOBILE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}

