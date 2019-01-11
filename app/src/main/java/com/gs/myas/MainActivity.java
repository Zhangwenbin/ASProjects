package com.gs.myas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.gs.libdemo.Utils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      System.out.println("------------------------"+Utils.showLog());

    }
}
