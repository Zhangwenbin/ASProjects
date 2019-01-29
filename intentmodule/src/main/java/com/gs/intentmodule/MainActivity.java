package com.gs.intentmodule;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private Context mContext;
   private Button sendBtn;
   private TextView broadcastContent;
    private Hellobroadcast receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people/1"));
//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("vnd.android.cursor.item/phone");
//        startActivity(intent);
        mContext=this;

        sendBtn=findViewById(R.id.button);
        broadcastContent=findViewById(R.id.editText);
        sendBtn.setOnClickListener(new SendBroadCastListener() {

        });

         receiver=new Hellobroadcast();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.eoeandroid.action.Broadcasttest");
        registerReceiver(receiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private  class  SendBroadCastListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String content=broadcastContent.getText().toString().trim();
            if (content.length()<1){
                Toast.makeText(mContext,broadcastContent.getHint(),1).show();
                return;
            }
            Intent intent=new Intent();
            intent.setAction("com.eoeandroid.action.Broadcasttest");
            intent.putExtra("content",content);
            sendBroadcast(intent);
        }
    }
}
