package com.wenjiehe.android_study.restart;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wenjiehe.android_study.R;

public class IPCActivity extends AppCompatActivity {

    /**
     * Android开发艺术探索
     * P65
     * */
    private Messenger mService;

    private Messenger mReplayTo = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 23:
                    Bundle bundle = msg.getData();
                    String str = bundle.getString("second");
                    Log.d("messengerhandle",str);

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

        Intent intent =new Intent(this,MessengerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);

            Message msg = Message.obtain(null,1);
            Bundle bundle =new Bundle();
            bundle.putString("first","wenjie");
            msg.setData(bundle);

            msg.replyTo = mReplayTo;

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
