package com.wenjiehe.android_study.restart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wenjiehe.android_study.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WindowActivity extends AppCompatActivity {

    @BindView(R.id.stretchbutton)
    StretchableFloatingButton stretchableFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new WindowManager.LayoutParams();

        stretchableFloatingButton.setOnClickListener(new StretchableFloatingButton.OnClickListener() {
            @Override
            public void onClick(StretchableFloatingButton button) {
                //Toast.makeText(WindowActivity.this, "22", Toast.LENGTH_SHORT).show();
                button.startScroll();
            }
        });
    }

}
