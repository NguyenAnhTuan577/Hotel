package com.example.hotel.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Util {
    public static int getNavigationHeight(Activity activity)
    {

        int navigationBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        if(!hasSoftKeys(activity.getWindowManager())) return 0;
        return  navigationBarHeight;
    }
    public static boolean hasSoftKeys(WindowManager windowManager){
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
