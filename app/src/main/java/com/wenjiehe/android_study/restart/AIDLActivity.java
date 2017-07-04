package com.wenjiehe.android_study.restart;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wenjiehe.android_study.MainActivity;
import com.wenjiehe.android_study.R;

import java.util.List;

public class AIDLActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView,mBookCount;

    private BookManager mBookManager;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    //包含Book对象的list
    private List<Book> mBooks;

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        mTextView = (TextView)findViewById(R.id.textview);
        mBookCount = (TextView)findViewById(R.id.books);
        mTextView.setOnClickListener(this);
        mBookCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textview:
                processAddBook();
                break;
            case R.id.books:
                processGetBookCount();
                break;
        }
    }

    public void processGetBookCount(){
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBookManager == null) return;

        try {
            mBookCount.setText(mBookManager.getBookCount()+"");
            Log.e(getLocalClassName(), "call server method getBookCount "+mBookManager.getBookCount());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void processAddBook(){
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBookManager == null) return;

        Book book = new Book();
        book.setBookName("APP研发录"+count);
        book.setBookPrice("30");
        book.setBookAuthor("Author"+count);
        count ++;
        try {
            mBookManager.addBook(book);
            Log.e(getLocalClassName(), book.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void attemptToBindService() {
        Intent intent = new Intent(this,AIDLService.class);
        //intent.setAction("com.example.zbtuo.aidl");
        //intent.setPackage("com.example.zbtuo.serverdemo1");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(getLocalClassName(), "service connected");
            mBookManager = BookManager.Stub.asInterface(iBinder);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.e(getLocalClassName(), mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };
}
