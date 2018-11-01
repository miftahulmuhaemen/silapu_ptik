package com.unlam.developerstudentclub.silapu.Box;

import android.app.Application;
import android.util.Log;

import com.unlam.developerstudentclub.silapu.BuildConfig;
import com.unlam.developerstudentclub.silapu.Entity.MyObjectBox;
import com.unlam.developerstudentclub.silapu.MainActivity;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }
        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
