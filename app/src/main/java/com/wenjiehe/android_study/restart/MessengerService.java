package com.wenjiehe.android_study.restart;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    public MessengerService() {
    }

    final Messenger messenger = new Messenger(new  Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    String str = bundle.getString("first");
                    Log.d("messengerhandle", str);

                    Messenger m = msg.replyTo;
                    Message ms = Message.obtain(null,23);
                    Bundle b = new Bundle();
                    b.putString("second","has received");
                    ms.setData(b);
                    try {
                        m.send(ms);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return messenger.getBinder();
    }
}
