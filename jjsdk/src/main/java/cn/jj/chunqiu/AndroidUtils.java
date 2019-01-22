package cn.jj.chunqiu;

import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.app.PendingIntent;
import android.app.AlarmManager;

public class AndroidUtils {
    public static void OnRestart(Activity context) {
        Intent mStartActivity = new Intent(context, context.getClass());
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
//        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static boolean checkRecordAudioPermission(Activity context) {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "cn.jj.chunqiu"));
        return permission;

    }

    public static ClipboardManager clipboard = null;

    // 鍚戝壀璐存澘涓坊鍔犳枃鏈�
    public void copyTextToClipboard(final Context activity, final String str)
            throws Exception {
        clipboard = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);
        ClipData textCd = ClipData.newPlainText("data", str);
        clipboard.setPrimaryClip(textCd);
    } // 浠庡壀璐存澘涓幏鍙栨枃鏈�

    public String pasteStr(final Context activity) {
        if (clipboard == null) {
            clipboard = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);
        }
        if (clipboard != null && clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            return item.getText().toString();
        }
        return "null";
    }

    //鍒ゆ柇鏄惁瀹夎浜嗗井淇�
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 鑾峰彇packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 鑾峰彇鎵�鏈夊凡瀹夎绋嬪簭鐨勫寘淇℃伅
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 鍒ゆ柇qq鏄惁鍙敤
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getImei();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
