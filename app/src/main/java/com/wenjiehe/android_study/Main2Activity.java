package com.wenjiehe.android_study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent in = getIntent();
        Bundle bd = in.getExtras();
        Boolean b =bd.getBoolean("firstActivity");
        Log.d("Main222",String.valueOf(b));

    }


    @Override
    public void onBackPressed() {

        setResult(3);
        super.onBackPressed();
    }
}

