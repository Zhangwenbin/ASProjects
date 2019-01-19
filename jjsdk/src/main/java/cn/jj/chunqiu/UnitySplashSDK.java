package cn.jj.chunqiu;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class UnitySplashSDK {
	 // 这是启动画面的图片，这里可以使任意一个View，可以根据自己的需要去自定义
    private ImageView bgView = null;
    private UnityPlayer mUnityPlayer = null;
    private static UnitySplashSDK mInstance;
    //private ProgressBar barShow;
    public static UnitySplashSDK getInstance() {
        if (null == mInstance) {
            synchronized (UnitySplashSDK.class) {
                if (null == mInstance) {
                    mInstance = new UnitySplashSDK();
                }
            }
        }
        return mInstance;
    }
    //called in mainactivity
    public void SetSplash(UnityPlayer unityPlayer)  
    {  
        mUnityPlayer = unityPlayer;         
        onShowSplash();
        //SetProgressBar(unityPlayer.currentActivity);  
    } 
    
    @SuppressLint("NewApi") 
     void onShowSplash() {
        if (bgView != null)
            return;
        try {
        	 Log.d("","UnityActivity_onShowSplash"); 
        	Resources r = UnityPlayer.currentActivity.getResources(); 
            // 设置启动内容，这里的内容可以自定义区修改
            bgView = new ImageView(UnityPlayer.currentActivity);           
            int splash_bg = r.getIdentifier("splash", "drawable",UnityPlayer.currentActivity.getPackageName());            		                      
            bgView.setImageResource(splash_bg);  
           // bgView.setScaleType(ScaleType.);  
            mUnityPlayer.addView(bgView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
     
    @SuppressLint("NewApi") 
    public void onHideSplash() {
        try {
            if (bgView == null)
                return;
            Log.d("","UnityActivity_onHideSplash");  
            UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
               @Override  
               public void run() {
                   mUnityPlayer.removeView(bgView);
                    bgView = null;
                  /*  if(barShow!=null)  
                    {  
                        mUnityPlayer.removeView(barShow);  
                        barShow = null;  
                    }  */
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //ProgressBar barshow;  
    public void SetProgressBar(Activity mainActivity)  
    { 
    	/* 
        Log.d("SetProgressBar_","SetProgressBar:"+mainActivity.getLocalClassName());  
        barShow = new ProgressBar(mUnityPlayer.currentActivity, null, android.R.attr.progressBarStyleLarge);  
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,-2);  
        barShow.setLayoutParams(lp);  
        WindowManager wm = (WindowManager) mainActivity.getSystemService(Context.WINDOW_SERVICE);  
        int w = wm.getDefaultDisplay().getWidth()/2; 
        int h = wm.getDefaultDisplay().getHeight()/2;  
        barShow.setPadding(w-40,h-60,0,0);  
        mUnityPlayer.addView(barShow);  */
    }  
}
