package com.wenjiehe.android_study.restart;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wenjiehe.android_study.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wenjiehe.android_study.R.id.play;

public class Button5Activity extends AppCompatActivity {

    @BindView(R.id.start)
    Button start;

    @BindView(R.id.pause)
    Button pause;

    @BindView(R.id.stop)
    Button stop;


    DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder =(DownloadService.DownloadBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button5);

        ButterKnife.bind(this);

        Intent intent =new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.start("http://123.206.214.17/xingji/xingji.apk");
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.stop();
            }
        });
    }
}
