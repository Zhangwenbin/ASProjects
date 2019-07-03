package com.gs.pn;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.os.*;

import com.unity3d.player.UnityPlayer;

public class SDKActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Resources resources = this.getResources();//加载res资源
        String packageName = this.getPackageName();//包名
        int id = resources.getIdentifier("activity_sdk", "layout", packageName);//获取Activity的layout
        super.onCreate(savedInstanceState);
        setContentView(id);
        //设置Activity中的EditText,为了打开软键盘
        final EditText textArea = (EditText)findViewById(resources.getIdentifier("textArea", "id", packageName));
        textArea.setText("sdfsdfsf");
        textArea.setBackgroundColor(0x00000000);
        textArea.setTextColor(0x00000000);
        textArea.setFocusableInTouchMode(true);
        textArea.requestFocus();
        textArea.setCursorVisible(true);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        textArea.addTextChangedListener(textWatcher);
        //点击了软键盘的完成
        textArea.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1,KeyEvent arg2)
            {
                Log.e("点击", "完成");
                SendData(0,arg0.getText().toString());
                finish();
                return true;
            }
        });
    }

    // 向unity返回数据
    void SendData(int code, String info)
    {
        UnityPlayer.UnitySendMessage("Plugins", "OnCustomInputAction",info);
    }
}