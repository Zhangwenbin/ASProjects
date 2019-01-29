package com.gs.helloeoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate");
        Button btn1=findViewById(R.id.button);
        btn1.setOnClickListener(this);
        Button btn2=findViewById(R.id.button2);
        btn2.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onClick(View view) {
          switch (view.getId()){
              case R.id.button:
                  Intent intent=new Intent(this,Main2Activity.class);
                  startActivity(intent);
                  break;
              case R.id.button2:
                  Intent intent1=new Intent(this,Main3Activity.class);
                  startActivityForResult(intent1,100);
                  break;
                  default:
                      break;
          }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==Activity.RESULT_OK){
            TextView textView=findViewById(R.id.textView);
            textView.setText(data.getExtras().getString("hello"));
        }
    }
}
