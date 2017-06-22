package com.example.animeshpatra.jsonparsing;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ANIMESH PATRA on 27-04-2017.
 */

public class NetworkState {
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
