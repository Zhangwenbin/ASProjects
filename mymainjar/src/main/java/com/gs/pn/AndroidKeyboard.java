package com.gs.pn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.unity3d.player.UnityPlayer;

public class AndroidKeyboard
{
    Activity context = null;
    InputMethodManager inputMethodManager = null;
    TextWatcher textWatcher = null;
    boolean mode = false;

    public AndroidKeyboard()
    {
        context = UnityPlayer.currentActivity;
        inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    //打开Activiy,并且显示输入法
    public void Open(final String text,final boolean mode)
    {
        if(context == null)
        {
            Log.e("unity", "context null when open keyboard");
            return ;
        }
        context.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e("unity", "1111111111111");
                Intent intent = new Intent();
                intent.setClassName(context, "com.gs.pn.SDKActivity");
                context.startActivity(intent);
            }
        });

    }

}