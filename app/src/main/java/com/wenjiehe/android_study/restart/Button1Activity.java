package com.wenjiehe.android_study.restart;

import android.app.Notification;
import android.app.NotificationManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.wenjiehe.android_study.R;

public class Button1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button1);


        getContentResolver().registerContentObserver(Uri.parse("content://sms"),true,new MyContentObserver(new Handler()));

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("hello")
                .setContentText("wenjie")
                .build();

        
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
        //getContentResolver().notifyChange(Uri.parse("content://sms/outbox") , null);
    }

    private final class  MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Cursor cursor =getContentResolver().query(Uri.parse("content://sms/outbox"),null,null,null,null);
            while(cursor.moveToNext()){
                StringBuilder stringBuilder =new StringBuilder();
                stringBuilder.append("content:=").append(cursor.getString(cursor.getColumnIndex("body")));
                Utils.ToastShow(Button1Activity.this,stringBuilder.toString());
                System.out.println(stringBuilder.toString());
            }
        }
    }
}
