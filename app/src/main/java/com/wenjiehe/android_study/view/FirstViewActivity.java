package com.wenjiehe.android_study.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wenjiehe.android_study.R;

public class FirstViewActivity extends AppCompatActivity {

    private FirstView firstview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_view);

        firstview = (FirstView)findViewById(R.id.firstview);

    }
}
