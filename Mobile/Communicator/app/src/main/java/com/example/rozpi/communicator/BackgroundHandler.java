package com.example.rozpi.communicator;

import android.app.Application;

/**
 * Created by Marcin Omelan on 28.03.2017.
 * Class that tells me if app is in the background or foreground
 */

class BackgroundHandler extends Application {
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}
