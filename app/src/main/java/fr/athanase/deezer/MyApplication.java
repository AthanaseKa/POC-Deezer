package fr.athanase.deezer;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by vincent on 04/04/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
