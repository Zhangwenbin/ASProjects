package com.gs.helloeoe;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button btn = findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                TextInputEditText input = findViewById(R.id.input);
                String value = input.getText().toString();
                data.putExtra("hello", value);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        });
    }
}
