package com.winthan.joketeller;

import android.app.Application;
import android.content.Context;

/**
 * Created by winthanhtike on 3/26/17.
 */

public class JokeTellerApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
