package com.gs.pn;

import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {
    static {
        System.loadLibrary("unity");
        System.loadLibrary("hotupdate");
        HotUpdate.getInstance().StartHook();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HotUpdate.getInstance().SetHotUpdatePath(this);
        Log.e("oncreate----------","ddddddddddd");
        super.onCreate(savedInstanceState);

    }
}
