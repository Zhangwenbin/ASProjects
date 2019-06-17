package com.gs.pn;

import android.app.Activity;
import android.util.Log;

import java.io.File;

public class HotUpdate
{
    private static HotUpdate hotUpdate = null;
    private static final String DllFile = "CqPersist/Cache/Assembly-CSharp.dll";

    public static HotUpdate getInstance()
    {
        if (hotUpdate == null)
        {
            hotUpdate = new HotUpdate();
        }
        return hotUpdate;
    }

    public void SetHotUpdatePath(Activity activity)
    {
        String appPath = activity.getExternalFilesDir(null).getAbsolutePath();
        Log.e("HotUpdate ", appPath);

        String dllPath = appPath + File.separator + DllFile;

        Log.e("HotUpdate ", dllPath);
        File file = new File(dllPath);
        if (file.exists())
        {
            Log.e("HotUpdate ",  dllPath);
            SetHotUpdatePath(dllPath);
        }
        else
        {
            Log.e("HotUpdate ", "dont exist");
        }
    }

    public native void SetHotUpdatePath(String path);

    public native void StartHook();
}