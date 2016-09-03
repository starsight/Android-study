package com.wenjiehe.android_study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wenjiehe.android_study.service.MyService;

public class Main2Activity extends AppCompatActivity {

    Button bt1;
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent in = getIntent();
        Bundle bd = in.getExtras();
        Boolean b = bd.getBoolean("firstActivity");
        Log.d("Main222", String.valueOf(b));

        bt1 = (Button) findViewById(R.id.m2_bt1);
        bt2 = (Button) findViewById(R.id.m2_bt2);

        final Intent intent = new Intent(Main2Activity.this, MyService.class);
        intent.setAction("com.wenjiehe.android_study.service.MY_SERVICE");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        setResult(3);
        super.onBackPressed();
    }
}

